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
            measuresControl1.UserName = this.User.Identity.Name;

            // Show only user's measures here
            measuresControl1.OnlyUser = true;
        }

    }

}