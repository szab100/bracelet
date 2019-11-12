using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Subgurim.Controles;

public partial class Events : System.Web.UI.Page
{

    protected override void OnPreInit(EventArgs e)
    {
        MasterPage sm = Page.Master;
        PlaceHolder script = (PlaceHolder)sm.FindControl("EventCheckerHolder");
        script.Visible = false;
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            // Initialize measure control
            // retrieve the username from the querystring
            string userName = this.Request.QueryString["UserName"];
            if (userName != null && userName.Length > 0)
            {
                LabelHeader.Text = "A " + userName + " nevű felhasználó eseményei";
                eventControl.UserName = userName;

                // Don't show all users measures here..
                eventControl.OnlyUser = true;
            }
            else
            {
                LabelHeader.Text = "Felhasználók eseményei";
                eventControl.UserName = this.User.Identity.Name;

                // Show all users measures here..
                eventControl.OnlyUser = false;
            }
        }

        // Hide Event Checker script by default (because we're already here at events page)
        SiteMaster sm = (SiteMaster)Page.Master;
    }
}