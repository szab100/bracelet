using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using braceletModel;

/// <summary>
/// Summary description for ClientServices
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]

[System.Web.Script.Services.ScriptService]
public class ClientServices : System.Web.Services.WebService {

    public ClientServices () {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    public int GetNumberOfUnhandledEvents()
    {
        try
        {
            if (User.IsInRole("Administrator"))
            {
                using (braceletEntities context = new braceletEntities())
                {
                    int numberOfUnhandledEvents = context.Events.Where(o => o.Status == (short)CommonNames.EventStatus.New).Count();
                    return numberOfUnhandledEvents;
                }
            }
            else return 0;
        }
        catch (Exception)
        {
            return 0;
        }
    }
    
}
