using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using braceletModel;

public partial class SiteMaster : System.Web.UI.MasterPage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!HttpContext.Current.User.IsInRole("Administrator"))
        {
            // Hide checker script
            EventCheckerHolder.Visible = false;
        }
    }

    [System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
    public static int GetNumberOfUnhandledEvents()
    {
        if (HttpContext.Current.User.IsInRole("Administrator"))
        {
            using (braceletEntities context = new braceletEntities())
            {
                int numberOfUnhandledEvents = context.Events.Where(o => o.Status == (short)CommonNames.EventStatus.New).Count();
                return numberOfUnhandledEvents;
            }
        }
        else return 0;
    }
}
