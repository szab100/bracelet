using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using braceletModel;
using System.Web.UI.DataVisualization.Charting;
using System.Web.UI.WebControls.Expressions;
using System.Drawing;
using System.Data;
using Subgurim.Controles;
using System.IO;

public partial class UserEvents : System.Web.UI.UserControl
{

    private string _userName = "";
    public string UserName
    {
        get { return _userName; }
        set { _userName = value; }
    }

    public bool _onlyUser = false;
    public bool OnlyUser
    {
        get { return _onlyUser; }
        set { _onlyUser = value; }
    }

    protected void Page_Init(object sender, EventArgs e)
    {
        this.Page.RegisterRequiresControlState(this);
    }
    protected override void LoadControlState(object savedState)
    {
        object[] ctlState = (object[])savedState;
        base.LoadControlState(ctlState[0]);
        _userName = (string)ctlState[1];
        _onlyUser = (bool)ctlState[2];
    }
    protected override object SaveControlState()
    {
        object[] ctlState = new object[3];
        ctlState[0] = base.SaveControlState();
        ctlState[1] = _userName;
        ctlState[2] = _onlyUser;
        return ctlState;
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {

            // User filter invisible first
            if (!CheckBoxUserFilter.Checked) UserFilterHolder.Visible = false;

            // Date filter invisible first
            if (!CheckBoxDateFilter.Checked) DateFilterHolder.Visible = false;

            // Range filter invisible first
            if (!CheckBoxRangeFilter.Checked) RangeFilterHolder.Visible = false;

            // Default sort by: Date Descending
            GridViewEvents.Sort("Raised", System.Web.UI.WebControls.SortDirection.Descending); 
        }

        // Initialize actual user
        String userName = this.UserName;
        MembershipUser user = Membership.GetUser(userName);

        // Prepare control if user is administrator / normal user
        if (this.Page.User.IsInRole("Administrator") && !OnlyUser)
        {

        }
        else
        {
            // Hide UserName field
            GridViewEvents.Columns[0].Visible = false;
            FilterTable.Rows[0].Visible = false;
        }

        // Hide Handle filed only when not Administrator
        if (! this.Page.User.IsInRole("Administrator"))
        {
            // Hide Handle field
            GridViewEvents.Columns[7].Visible = false;
        }

        foreach (GridViewRow gridViewRow in GridViewEvents.Rows)
        {
            // I can see all elements of my row here as if I am traversing on GridViewEvents
            
            ImageButton recButton = (ImageButton) gridViewRow.FindControl("ButtonDownloadRec");
            if (recButton != null) ScriptManager.GetCurrent(this.Page).RegisterPostBackControl(recButton);
            
        }

    }

    private void SetTextBoxHints(TextBox textBox, string defaultText)
    {
        textBox.Attributes.Add("onfocus", "clearText(this,'" + defaultText + "')");
        textBox.Attributes.Add("onblur", "resetText(this,'" + defaultText + "')");
        
        if (textBox.Text == "")
        {
            textBox.Text = defaultText;
            textBox.ForeColor = System.Drawing.Color.Silver;
        }
        else if (textBox.Text == defaultText)
        {
            textBox.ForeColor = System.Drawing.Color.Silver;
        }
        else
        {
            textBox.ForeColor = System.Drawing.Color.Black;
        }
    }

    protected void GridViewEvents_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            Event ev = e.Row.DataItem as Event;

            switch ((CommonNames.EventStatus)ev.Status)
            {
                case CommonNames.EventStatus.New:
                    e.Row.BackColor = Color.LightPink;
                    e.Row.FindControl("TextBoxHandle").Visible = true;
                    e.Row.FindControl("ButtonHandle").Visible = true;
                    break;

                case CommonNames.EventStatus.Handled:
                    e.Row.BackColor = Color.LightGreen;
                    e.Row.FindControl("TextBoxHandle").Visible = false;
                    e.Row.FindControl("ButtonHandle").Visible = false;
                    break;

                default:
                    e.Row.BackColor = Color.Gray;
                    break;
            }

            // Set textboxhandle hints
            TextBox tb = (TextBox)e.Row.FindControl("TextBoxHandle");
            SetTextBoxHints(tb, "Megjegyzés..");
        }
    }

    protected void GridViewEvents_OnRowCommand(object sender, GridViewCommandEventArgs e)
    {

        //// Only handle our custom commands here!
        if (e.CommandName.Equals("Handle") || e.CommandName.Equals("Position") || e.CommandName.Equals("DownloadRecording"))
        {
            int index = Convert.ToInt32(e.CommandArgument);
            GridViewRow gvRow = ((GridView)sender).Rows[index];
            Int32 eventID = (Int32) GridViewEvents.DataKeys[index].Value;

            using (braceletEntities context = new braceletEntities())
            {
                switch (e.CommandName)
                {
                    case "Handle":
                        Event ev = context.Events.Where(o => o.ID == eventID).First();
                        ev.Status = (byte)CommonNames.EventStatus.Handled;
                        ev.Note = ((TextBox)gvRow.FindControl("TextBoxHandle")).Text;
                        context.SaveChanges();
                        GridViewEvents.DataBind();
                        GridViewPanel.Update();
                        break;

                    case "Position":
                        Event evSelected = context.Events.Where(o => o.ID == eventID).First();
                        GMapHolder.Visible = true;

                        // Set selected row
                        GridView gv = (GridView)sender;
                        gv.SelectedIndex = index;

                        // Add GUI controls
                        GMap.addGMapUI(new GMapUI());

                        // Set center point
                        if (evSelected.PositionLatitude != null && evSelected.PositionLongitude != null)
                            GMap.setCenter(new GLatLng(evSelected.PositionLatitude.Value, evSelected.PositionLongitude.Value));

                        // Set zoom level
                        GMap.GZoom = 15;

                        // Add center marker
                        GMap.resetMarkers();
                        if (evSelected.PositionLatitude != null && evSelected.PositionLongitude != null)
                        {
                            GMap.addGMarker(new GMarker(new GLatLng(evSelected.PositionLatitude.Value, evSelected.PositionLongitude.Value)));
                        }
                        break;

                    case "DownloadRecording":
                        if (HttpContext.Current.User.IsInRole("Administrator"))
                        {
                            Recording rec = context.Recordings.Where(o => o.EventID == eventID).FirstOrDefault();

                            if (rec != null)
                            {
                                FileStream fStream = null;
                                try
                                {
                                    String fileName = "C:\\BraceletRecordings\\" + rec.FileName;

                                    HttpResponse r = HttpContext.Current.Response;
                                    Response.ContentType = "audio/mpeg";
                                    Response.AppendHeader("Content-Disposition", "attachment; filename=" + rec.FileName);
                                    fStream = new FileStream(fileName, FileMode.Open);
                                    fStream.CopyTo(Response.OutputStream);
                                    Response.End();
                                }
                                catch (IOException ex)
                                {
                                    throw new IOException("Cannot find the selected recording.", ex);
                                }
                                finally
                                {
                                    // Always close the fileStream
                                    if (fStream != null) fStream.Close();
                                }
                                
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
        } // End of If(e.commandName.Equals("..") || ...)

    }

    protected void GridViewEvents_DataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            if (e.Row.Cells[0].Text.Contains("sometext"))
            {
                e.Row.Cells[0].Font.Bold = true;
            }
        }
    }


    protected void FilterCheckBox_CheckedChanged(object sender, EventArgs e)
    {

        // User filter panel
        if (CheckBoxUserFilter.Checked)
        {
            UserFilterHolder.Visible = true;
        }
        else
        {
            UserFilterHolder.Visible = false;
            TextBoxUserName.Text = "";
        }

        // Date filter panel
        if (CheckBoxDateFilter.Checked)
        {
            DateFilterHolder.Visible = true;
        }
        else
        {
            DateFilterHolder.Visible = false;
            TextBoxDateFrom.Text = "";
            TextBoxDateTo.Text = "";
        }

        // Range filter panel
        if (CheckBoxRangeFilter.Checked)
        {
            RangeFilterHolder.Visible = true;
        }
        else
        {
            RangeFilterHolder.Visible = false;
            TextBoxRangeFrom.Text = "";
            TextBoxRangeTo.Text = "";
        }

        //FilterPanel.Update();
    }

    protected string GetEventCode(object obj)
    {
        if (obj != null)
        {
            byte status = 0;
            Byte.TryParse(obj.ToString(), out status);

            switch ((CommonNames.EventCode) status)
            {
                case CommonNames.EventCode.Panic:
                    return "Pánik riasztás";

                case CommonNames.EventCode.ValueOverTolerance:
                    return "Tolerancián túl";

                default:
                    return "N/A";
            }
        }
        else return "N/A";
    }

    protected bool GetRecButtonVisible(object obj)
    {
        if (obj != null)
        {
            int id = 0;
            Int32.TryParse(obj.ToString(), out id);

            if (HttpContext.Current.User.IsInRole("Administrator"))
            {
                using (braceletEntities context = new braceletEntities())
                {
                    Recording rec = context.Recordings.Where(o => o.EventID == id).FirstOrDefault();

                    if (rec != null) return true;
                    else return false;
                }
            }

        }

        return false;
    }

    protected string GetStatus(object obj)
    {
        if (obj != null)
        {
            byte status = 0;
            Byte.TryParse(obj.ToString(), out status);

            switch ((CommonNames.EventStatus)status)
            {
                case CommonNames.EventStatus.New:
                    return "Új";

                case CommonNames.EventStatus.Handled:
                    return "Kezelt";

                default:
                    return "N/A";
            }

        }
        else return "N/A";
    }

    protected void FilterEventsByUser(object sender, CustomExpressionEventArgs e)
    {

        // Check if User is Administrator / Normal user
        if (this.Page.User.IsInRole("Administrator") && !OnlyUser)
        {
            // Get username as parameter
            String username = "";
            if (e.Values["username"] != null) username = e.Values["username"].ToString();

            e.Query = from m in e.Query.Cast<Event>()
                      where m.aspnet_Users.UserName.Contains(username)
                      select m;
        }
        else
        {
            e.Query = from m in e.Query.Cast<Event>()
                      where m.aspnet_Users.UserName == this.UserName
                      select m;
        }

    }

    protected void FilterEventsByRange(object sender, CustomExpressionEventArgs e)
    {

        // Parse all parameters if available
        float min = float.MinValue;
        float max = float.MaxValue;
        bool enabled = false;

        try
        {
            if (e.Values["enabled"] != null) enabled = Boolean.Parse(e.Values["enabled"].ToString());
            if (e.Values["rangeFrom"] != null) min = float.Parse(e.Values["rangeFrom"].ToString());
            if (e.Values["rangeTo"] != null) max = float.Parse(e.Values["rangeTo"].ToString());
        }
        catch (Exception)
        {
        }

        if (enabled)
        {

            e.Query = from m in e.Query.Cast<Event>()
                        where m.Level >= min && m.Level <= max
                        select m;
        } // End of if(..)
        else
        {
            e.Query = from m in e.Query.Cast<Event>()
                      select m;
        }

    }
}