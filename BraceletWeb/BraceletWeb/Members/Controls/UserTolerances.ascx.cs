using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using braceletModel;
using System.Drawing;

public partial class UserTolerances : System.Web.UI.UserControl
{
    private string _userName = "";
    public string UserName
    {
        get { return _userName; }
        set { _userName = value; }
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
    }
    protected override object SaveControlState()
    {
        object[] ctlState = new object[2];
        ctlState[0] = base.SaveControlState();
        ctlState[1] = _userName;
        return ctlState;
    }

    protected void Page_Load(object sender, EventArgs e)
    {

        if (!this.IsPostBack)
        {
            ToleranceValuesHolder.Visible = false;
            ButtonSubmit.Visible = false;
        }
    }

    protected void DropDownToleranceType_SelectedIndexChanged(object sender, EventArgs e)
    {
        // Load user data
        if (this.UserName.Length > 0)
        {
            string selectedType = DropDownToleranceType.SelectedValue;
            Nullable<Double> low = null;
            Nullable<Double> high = null;

            // Reset textbox values
            TextBoxHigh.Text = "";
            TextBoxLow.Text = "";

            // Reset message
            MessageLabel.Text = "";

            using (braceletEntities context = new braceletEntities())
            {
                Tolerance tol = context.Tolerances.Where(o=> o.aspnet_Users.UserName == UserName).FirstOrDefault();
                if (tol == null) tol = new Tolerance();

                // Make tolerance values visible
                ToleranceValuesHolder.Visible = true;

                switch (selectedType)
                {
                    case "acc":
                        if(tol.AccelerationTolerance.HasValue) high = tol.AccelerationTolerance.Value;
                        LowValueLabelHolder.Visible = false;
                        TextBoxLow.Visible = false;
                        ValidatorLow.Enabled = false;
                        ButtonSubmit.Visible = true;
                        break;

                    case "blox":
                        if (tol.BloodOxygenToleranceLow.HasValue) low = tol.BloodOxygenToleranceLow.Value;
                        if (tol.BloodOxygenToleranceHigh.HasValue) high = tol.BloodOxygenToleranceHigh.Value;
                        LowValueLabelHolder.Visible = true;
                        TextBoxLow.Visible = true;
                        ValidatorLow.Enabled = true;
                        ButtonSubmit.Visible = true;
                        break;

                    case "temp":
                        if (tol.TempToleranceLow.HasValue) low = tol.TempToleranceLow.Value;
                        if (tol.TempToleranceHigh.HasValue) high = tol.TempToleranceHigh.Value;
                        LowValueLabelHolder.Visible = true;
                        TextBoxLow.Visible = true;
                        ValidatorLow.Enabled = true;
                        ButtonSubmit.Visible = true;
                        break;

                    case "pulse":
                        if (tol.PulseToleranceLow.HasValue) low = tol.PulseToleranceLow.Value;
                        if (tol.PulseToleranceHigh.HasValue) high = tol.PulseToleranceHigh.Value;
                        LowValueLabelHolder.Visible = true;
                        TextBoxLow.Visible = true;
                        ValidatorLow.Enabled = true;
                        ButtonSubmit.Visible = true;
                        break;

                    default:
                        // Make tolerance values hidden
                        ToleranceValuesHolder.Visible = false;
                        ButtonSubmit.Visible = false;
                        break;

                } // End of switch(..)

                // Set textbox values
                if (high != null) TextBoxHigh.Text = high.ToString();
                else TextBoxHigh.Text = "-nincs-";
                if (low != null) TextBoxLow.Text = low.ToString();
                else TextBoxLow.Text = "-nincs-";
            }
            
        }
    }

    protected void ButtonSubmit_Click(object sender, EventArgs e)
    {
        // Save values
        if (Page.IsValid)
        {

            using (braceletEntities context = new braceletEntities())
            {
                bool newTolerance = false;
                bool noAction = false;

                Tolerance tol = context.Tolerances.Where(o => o.aspnet_Users.UserName == UserName).FirstOrDefault();
                if (tol == null) 
                {
                    newTolerance = true;
                    tol = new Tolerance();
                    tol.UserID = context.aspnet_Users.Where(o=> o.UserName == UserName).FirstOrDefault().UserId;
                }

                string selectedType = DropDownToleranceType.SelectedValue;
                double low = 0;
                double high = 0;

                // Try to parse values
                if(TextBoxLow.Visible) double.TryParse(TextBoxLow.Text, out low);
                double.TryParse(TextBoxHigh.Text, out high);

                switch (selectedType)
                {
                    case "acc":
                        tol.AccelerationTolerance = high;
                        break;

                    case "blox":
                        tol.BloodOxygenToleranceLow = low;
                        tol.BloodOxygenToleranceHigh = high;
                        break;

                    case "temp":
                        tol.TempToleranceLow = low;
                        tol.TempToleranceHigh = high;
                        break;

                    case "pulse":
                        tol.PulseToleranceLow = (int)low;
                        tol.PulseToleranceHigh = (int)high;
                        break;

                    default:
                        noAction = true;
                        break;
                } // End of switch(..)

                // Save user's tolerance
                if(newTolerance) context.Tolerances.AddObject(tol);
                context.SaveChanges();

                if (!noAction)
                {
                    MessageLabel.Text = "A új toleranciamaszk értékek sikeresen elmentve.";
                    MessageLabel.ForeColor = Color.Green;
                }

            } // End of using(..)
        }
    }
}