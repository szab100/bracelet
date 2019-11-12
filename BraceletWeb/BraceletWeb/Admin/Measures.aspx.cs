using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using braceletModel;

public partial class Measures : System.Web.UI.Page
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
                LabelHeader.Text = "A " + userName + " nevű felhasználó mérései";
                measuresControlAdmin.UserName = userName;

                // Don't show all users measures here..
                measuresControlAdmin.OnlyUser = true;
            }
            else
            {
                LabelHeader.Text = "Felhasználók mérései";
                measuresControlAdmin.UserName = this.User.Identity.Name;

                // Show all users measures here..
                measuresControlAdmin.OnlyUser = false;
            }

        }

    }

}