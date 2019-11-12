using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using braceletModel;

public partial class Tolerances : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

        if (!IsPostBack)
        {
            // Initialize measure control
            // retrieve the username from the querystring
            string userName = this.Request.QueryString["UserName"];
            if (userName!=null && userName.Length > 0)
            {
                LabelHeader.Text = "A " + userName + " nevű felhasználó tolerancia-szintjei";
                tolerancesControlAdmin.UserName = userName;
            }
            else
            {
                LabelHeader.Text = "Saját felhasználó tolerancia-szintjei";
                tolerancesControlAdmin.UserName = this.User.Identity.Name;
            }

        }

    }

}