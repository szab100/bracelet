using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Web.Profile;
using System.Drawing;
using braceletModel;

public partial class Admin_Admin : System.Web.UI.Page
{
    private MembershipUserCollection allRegisteredUsers = Membership.GetAllUsers();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!this.IsPostBack)
        {
            lblOnlineUsers.Text = Membership.GetNumberOfUsersOnline().ToString();
            lblTotalUsers.Text = allRegisteredUsers.Count.ToString();
            string[] alph = "A;B;C;D;E;F;G;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;Összes".Split(';');
            rptAlphabetBar.DataSource = alph;
            rptAlphabetBar.DataBind();

            // Default contextkey for autocompleteExtender
            autoComplete1.ContextKey = ddlUserSearchTypes.SelectedValue;
        }
    }

    protected void rptAlphabetBar_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        gvUsers.Attributes.Add("SearchByEmail", false.ToString());
        if (e.CommandArgument.ToString().Length == 1)
        {
            gvUsers.Attributes.Add("SearchText", e.CommandArgument.ToString() + "%");
            this.BindAllUsers(false);
        }
        else
        {
            gvUsers.Attributes.Add("SearchText", "");
            this.BindAllUsers(false);
        }
    }

    protected void OnRowCommand(object sender, GridViewCommandEventArgs e)
    {
        int index = Convert.ToInt32(e.CommandArgument);
        GridViewRow gvRow = gvUsers.Rows[index];
        string userName = gvRow.Cells[0].Text;

        using (braceletEntities context = new braceletEntities())
        {
            switch (e.CommandName)
            {
                case "ForceMeasure":
                    // TODO: insert actionQueue
                    ActionQueue action = new ActionQueue();
                    action.UserID = context.aspnet_Users.Where(o => o.UserName == userName).FirstOrDefault().UserId;
                    action.Description = "Manuális mérés indítás";
                    action.Status = (short)CommonNames.ActionStatus.New;
                    action.Action = (short) CommonNames.ActionType.ForceMeasure;
                    action.Created = DateTime.Now;
                    context.ActionQueues.AddObject(action);
                    context.SaveChanges();

                    // Inform user
                    LabelPopupMessage.Text = "Az akció ütemezése sikeresen megtörtént!";
                    LabelPopupMessage.ForeColor = Color.Green;
                    ModalPopupExtender1.Enabled = true;
                    ModalPopupExtender1.Show();
                    break;

                default:
                    break;
            }
        }
        
    }

    private void BindAllUsers(bool reloadAllUsers)
    {
        MembershipUserCollection allUsers = null;
        if (reloadAllUsers)
            allUsers = Membership.GetAllUsers();
        string searchText = "";
        if (!string.IsNullOrEmpty(gvUsers.Attributes["SearchText"]))
            searchText = gvUsers.Attributes["SearchText"];
        bool searchByEmail = false;
        if (!string.IsNullOrEmpty(gvUsers.Attributes["SearchByEmail"]))
            searchByEmail = bool.Parse(gvUsers.Attributes["SearchByEmail"]);
        if (searchText.Length > 0)
        {
            if (searchByEmail)
                allUsers = Membership.FindUsersByEmail(searchText);
            else
                allUsers = Membership.FindUsersByName(searchText.ToUpper(System.Globalization.CultureInfo.InvariantCulture));
        }
        else
        {
            allUsers = allRegisteredUsers;
        }
        gvUsers.DataSource = allUsers;
        gvUsers.DataBind();
    }

    protected void btnSearch_Click(object sender, EventArgs e)
    {
        bool searchByEmail = (ddlUserSearchTypes.SelectedValue == "E-mail");
        gvUsers.Attributes.Add("SearchText", "%" + txtSearchText.Text + "%");
        gvUsers.Attributes.Add("SearchByEmail", searchByEmail.ToString());
        BindAllUsers(false);
    }

    protected void gvUsers_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        string userName = gvUsers.DataKeys[e.RowIndex].Value.ToString();
        ProfileManager.DeleteProfile(userName);
        Membership.DeleteUser(userName);
        BindAllUsers(true);
        lblTotalUsers.Text = allRegisteredUsers.Count.ToString();
    }

    protected void gvUsers_RowCreated(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            ImageButton btn = e.Row.Cells[10].Controls[0] as ImageButton;
            btn.OnClientClick = "if (confirm('Biztosan törölni szeretnéd ezt a felhasználót?') == false) return false;";
        }
    }

    [System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
    public static string[] GetCompletionList(string prefixText, int count, string contextKey)
    {
        MembershipUserCollection coll = null;
        if (contextKey.Equals("E-mail"))
        {
            coll = Membership.FindUsersByEmail("%" + prefixText + "%");
        }
        else
        {
            coll = Membership.FindUsersByName(prefixText + "%");
        }

        String[] names = new String[coll.Count];
        int i=0;
        foreach (MembershipUser u in coll)
        {
            if(contextKey.Equals("E-mail")) names[i++] = u.Email;
            else names[i++] = u.UserName;
        }

        return names;
    }
    protected void ddlUserSearchTypes_SelectedIndexChanged(object sender, EventArgs e)
    {
        autoComplete1.ContextKey = ((DropDownList)sender).SelectedValue;
    }
}