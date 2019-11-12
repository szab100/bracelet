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

public partial class UserMeasures : System.Web.UI.UserControl
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

            initChartDropDownLists();

            // Default sort by: Date Descending
            GridViewMeasures.Sort("Measured", System.Web.UI.WebControls.SortDirection.Descending); 
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
            GridViewMeasures.Columns[0].Visible = false;
            FilterTable.Rows[0].Visible = false;
        }

    }

    private void initChartDropDownLists()
    {
        // Populate chart types dropdownlists
        DropDownAccelerationChartType.Items.Clear();
        DropDownTemperatureChartType.Items.Clear();
        DropDownPulseChartType.Items.Clear();
        DropDownBloodOxygenChartType.Items.Clear();
        foreach (string name in Enum.GetNames(typeof(SeriesChartType)))
        {
            DropDownAccelerationChartType.Items.Add(name);
            DropDownTemperatureChartType.Items.Add(name);
            DropDownPulseChartType.Items.Add(name);
            DropDownBloodOxygenChartType.Items.Add(name);
        }

        // Preset Colors & set colorpicker textboxes to read-only (workaround)
        TextBoxCPAcceleration.Text = "00FF66";
        TextBoxCPTemperature.Text = "00CCFF";
        TextBoxCPPulse.Text = "FFFF00";
        TextBoxCPBloodOxygen.Text = "FF0000";

        TextBoxCPAcceleration.Attributes.Add("readonly", "readonly");
        TextBoxCPTemperature.Attributes.Add("readonly", "readonly");
        TextBoxCPPulse.Attributes.Add("readonly", "readonly");
        TextBoxCPBloodOxygen.Attributes.Add("readonly", "readonly");

        // Hide them first
        if (!CheckBoxChartAcceleration.Checked) ChartAccelerationHolder.Visible = false;
        if (!CheckBoxChartTemperature.Checked) ChartTemperatureHolder.Visible = false;
        if (!CheckBoxChartPulse.Checked) ChartPulseHolder.Visible = false;
        if (!CheckBoxChartBloodOxygen.Checked) ChartBloodOxygenHolder.Visible = false;

    }

    // Chart Type DropDownList changed
    protected void dropdownChartType_SelectedIndexChanged(object sender, EventArgs e)
    {
        setChartSeries();
    }

    private void setChartSeries()
    {
        // Fist, clear all existing series
        ChartMeasures.Series.Clear();

        // Acceleration
        if(CheckBoxChartAcceleration.Checked ) 
        {
            Series newSeries = new Series("Gyorsulás");
            newSeries.XValueMember = "Measured";
            newSeries.YValueMembers = "Acceleration";
            newSeries.ChartType = (SeriesChartType)DropDownAccelerationChartType.SelectedIndex;
            newSeries.Color = ColorTranslator.FromHtml("#" + TextBoxCPAcceleration.Text);
            ChartMeasures.Series.Add(newSeries);
        }

        // Temperature
        if (CheckBoxChartTemperature.Checked)
        {
            Series newSeries = new Series("Hőmérséklet");
            newSeries.XValueMember = "Measured";
            newSeries.YValueMembers = "Temperature";
            newSeries.ChartType = (SeriesChartType)DropDownTemperatureChartType.SelectedIndex;
            newSeries.Color = ColorTranslator.FromHtml("#" + TextBoxCPTemperature.Text);
            ChartMeasures.Series.Add(newSeries);
        }

        // Pulse
        if (CheckBoxChartPulse.Checked)
        {
            Series newSeries = new Series("Pulzus");
            newSeries.XValueMember = "Measured";
            newSeries.YValueMembers = "Pulse";
            newSeries.ChartType = (SeriesChartType)DropDownPulseChartType.SelectedIndex;
            newSeries.Color = ColorTranslator.FromHtml("#" + TextBoxCPPulse.Text);
            ChartMeasures.Series.Add(newSeries);
        }

        // BloodOxygen
        if (CheckBoxChartBloodOxygen.Checked)
        {
            Series newSeries = new Series("Véroxigén");
            newSeries.XValueMember = "Measured";
            newSeries.YValueMembers = "BloodOxygen";
            newSeries.ChartType = (SeriesChartType)DropDownBloodOxygenChartType.SelectedIndex;
            newSeries.Color = ColorTranslator.FromHtml("#" + TextBoxCPBloodOxygen.Text);
            ChartMeasures.Series.Add(newSeries);
        }

        if (ChartMeasures.Series.Count == 0) ChartHolder.Visible = false;
        else ChartHolder.Visible = true;
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

    protected void ChartCheckBox_CheckedChanged(object sender, EventArgs e)
    {

        // Chart - Acceleration
        if (CheckBoxChartAcceleration.Checked) ChartAccelerationHolder.Visible = true;
        else ChartAccelerationHolder.Visible = false;

        // Chart - Temperature
        if (CheckBoxChartTemperature.Checked) ChartTemperatureHolder.Visible = true;
        else ChartTemperatureHolder.Visible = false;

        // Chart - Pulse
        if (CheckBoxChartPulse.Checked) ChartPulseHolder.Visible = true;
        else ChartPulseHolder.Visible = false;

        // Chart - BloodOxygen
        if (CheckBoxChartBloodOxygen.Checked) ChartBloodOxygenHolder.Visible = true;
        else ChartBloodOxygenHolder.Visible = false;

        //GridViewPanel.Update();
    }

    protected void FilterMeasuresByUser(object sender, CustomExpressionEventArgs e)
    {

        // Check if User is Administrator / Normal user
        if (this.Page.User.IsInRole("Administrator") && !OnlyUser)
        {
            // Get username as parameter
            String username = "";
            if (e.Values["username"] != null) username = e.Values["username"].ToString();

            e.Query = from m in e.Query.Cast<Measure>()
                      where m.aspnet_Users.UserName.Contains(username)
                      select m;
        }
        else
        {
            e.Query = from m in e.Query.Cast<Measure>()
                      where m.aspnet_Users.UserName == this.UserName
                      select m;
        }

    }

    protected void FilterMeasuresByRange(object sender, CustomExpressionEventArgs e)
    {

        // Parse all parameters if available
        float min = float.MinValue;
        float max = float.MaxValue;
        string selectedColumn = "";
        bool enabled = false;

        try
        {
            if (e.Values["enabled"] != null) enabled = Boolean.Parse(e.Values["enabled"].ToString());
            if (e.Values["column"] != null) selectedColumn = e.Values["column"].ToString();
            if (e.Values["rangeFrom"] != null) min = float.Parse(e.Values["rangeFrom"].ToString());
            if (e.Values["rangeTo"] != null) max = float.Parse(e.Values["rangeTo"].ToString());
        }
        catch (Exception)
        {
        }

        if (enabled)
        {
            switch (selectedColumn)
            {
                case "acc":
                    e.Query = from m in e.Query.Cast<Measure>()
                              where m.Acceleration >= min && m.Acceleration <= max
                              select m;
                    break;

                case "blox":
                    e.Query = from m in e.Query.Cast<Measure>()
                              where m.BloodOxygen >= min && m.BloodOxygen <= max
                              select m;
                    break;

                case "temp":
                    e.Query = from m in e.Query.Cast<Measure>()
                              where m.Temperature >= min && m.Temperature <= max
                              select m;
                    break;

                case "pulse":
                    e.Query = from m in e.Query.Cast<Measure>()
                              where m.Pulse >= (int)min && m.Pulse <= (int)max
                              select m;
                    break;

                default:
                    e.Query = from m in e.Query.Cast<Measure>()
                              select m;
                    break;

            } // End of switch(..)
        } // End of if(..)
        else
        {
            e.Query = from m in e.Query.Cast<Measure>()
                      select m;
        }

    }

    protected void ChartMeasures_Load(object sender, EventArgs e)
    {
        setChartSeries();
    }
}