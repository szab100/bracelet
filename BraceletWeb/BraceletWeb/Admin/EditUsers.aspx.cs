using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;

public partial class Admin_EditUsers : System.Web.UI.Page
{
    string userName = "";

    protected void Page_Load(object sender, EventArgs e)
    {
        // retrieve the username from the querystring
        userName = this.Request.QueryString["UserName"];

        lblRolesOK.Visible = false;

        if (!this.IsPostBack)
        {
            UserProfile1.UserName = userName;
            MembershipUser user = Membership.GetUser(userName);
            lblUserName.Text = user.UserName;
            lnkEmailAddress.Text = user.Email;
            lnkEmailAddress.NavigateUrl = "mailto:" + user.Email;
            lblRegistered.Text = user.CreationDate.ToString("f");
            lblLastLogin.Text = user.LastLoginDate.ToString("f");
            lblLastActivity.Text = user.LastActivityDate.ToString("f");
            chkIsLockedOut.Checked = user.IsLockedOut;
            chkIsLockedOut.Enabled = user.IsLockedOut;
            chkIsOnlineNow.Checked = user.IsOnline;
            chkIsApproved.Checked = user.IsApproved;
            BindRoles();
        }
    }

    private void BindRoles()
    {
        chklRoles.DataSource = Roles.GetAllRoles();
        chklRoles.DataBind();
        foreach (string role in Roles.GetRolesForUser(userName))
            chklRoles.Items.FindByText(role).Selected = true;
    }

    protected void btnUpdate_Click(object sender, EventArgs e)
    {
        // first remove the user from all roles...
        string[] currentRoles = Roles.GetRolesForUser(userName);

        if (currentRoles.Length > 0)
            Roles.RemoveUserFromRoles(userName, currentRoles);
        // and then add the user to the selected roles
        List<string> newRoles = new List<string>();
        foreach (ListItem item in chklRoles.Items)
        {
            if (item.Selected)
                newRoles.Add(item.Text);
        }
        Roles.AddUserToRoles(userName, newRoles.ToArray());

        lblRolesOK.Visible = true;
    }

    protected void btnCreate_Click(object sender, EventArgs e)
    {
        if (!Roles.RoleExists(txtNewRole.Text.Trim()))
        {
            Roles.CreateRole(txtNewRole.Text.Trim());
            BindRoles();
        }
    }

    protected void buttonChPass_Click(object sender, EventArgs e)
    {
        if(this.User.IsInRole("Administrator"))
        {
            String newPass = textBoxNewPassword.Text;
            String newPassVerify = textBoxNewPassword.Text;

            if (newPass.Equals(newPassVerify))
            {
                // Change password
                MembershipUser user = Membership.GetUser(userName);

                try
                {
                    user.ChangePassword(user.ResetPassword(), newPass);
                }
                catch (Exception ex)
                {

                    ComparePasswordValidator.ErrorMessage = ex.Message;
                    ComparePasswordValidator.IsValid = false;
                }
            }
            else
            {
                ComparePasswordValidator.ErrorMessage = "A két jelszó nem egyezik!";
                ComparePasswordValidator.IsValid = false;
            }
        }
    }

    protected void chkIsApproved_CheckedChanged(object sender, EventArgs e)
    {
        MembershipUser user = Membership.GetUser(userName);
        user.IsApproved = chkIsApproved.Checked;
        Membership.UpdateUser(user);
    }

    protected void chkIsLockedOut_CheckedChanged(object sender, EventArgs e)
    {
        if (!chkIsLockedOut.Checked)
        {
            MembershipUser user = Membership.GetUser(userName);
            user.UnlockUser();
            chkIsLockedOut.Enabled = false;
        }
    }

}