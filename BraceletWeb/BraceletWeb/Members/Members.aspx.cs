using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Members_Members : System.Web.UI.Page
{

    protected void Page_Load(object sender, EventArgs e)
    {
        ChangePasswordHyperLink.NavigateUrl = "~/Account/ChangePassword.aspx?ReturnUrl=" + HttpUtility.UrlEncode(Request.QueryString["ReturnUrl"]);
        
        if (!this.IsPostBack)
        {
            UserProfile1.UserName = this.User.Identity.Name;
        }
    }

}