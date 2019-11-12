using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Events : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            // Initialize measure control
            eventControl.UserName = this.User.Identity.Name;

            // Show only this user's events
            eventControl.OnlyUser = true;
        }
    }
}