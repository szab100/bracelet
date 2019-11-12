using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class UserProfile : System.Web.UI.UserControl
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

        lblProfileOK.Visible = false;

        if (!this.IsPostBack)
        {
            // if the UserName property contains an emtpy string, retrieve the profile
            // for the current user, otherwise for the specified user
            ProfileCommon profile = this.Profile;
            if (this.UserName.Length > 0)
                profile = this.Profile.GetProfile(this.UserName);
            txtFirstName.Text = profile.FirstName;
            txtLastName.Text = profile.LastName;
            ddlGenders.SelectedValue = profile.Gender;
            if (profile.BirthDate != DateTime.MinValue)
                txtBirthDate.Text = profile.BirthDate.ToShortDateString();
            ddlOccupations.SelectedValue = profile.Occupation;
            txtWebsite.Text = profile.Website;
            txtStreet.Text = profile.Address.Street;
            txtCity.Text = profile.Address.City;
            txtPostalCode.Text = profile.Address.PostalCode;
            txtCounty.Text = profile.Address.County;
            txtPhone.Text = profile.Contacts.Phone;
            txtFax.Text = profile.Contacts.Fax;
            txtMobile.Text = profile.Contacts.Mobile;

            // If user has picture uploaded
            if (profile.Picture.ImagePath.Length >= 0)
            {
                ImageProfile.ImageUrl = profile.Picture.ImagePath;
                ImageProfile.AlternateText = profile.Picture.ImageName;
            }
            else ImageProfile.ImageUrl = "~/Images/DefaultUser.png";
        }
    }

    public void Save()
    {
        // if the UserName property contains an emtpy string, save the current user's
        // profile, othwerwise save the profile for the specified user
        ProfileCommon profile = this.Profile;
        if (this.UserName.Length > 0)
            profile = this.Profile.GetProfile(this.UserName);
        profile.FirstName = txtFirstName.Text;
        profile.LastName = txtLastName.Text;
        profile.Gender = ddlGenders.SelectedValue;
        if (txtBirthDate.Text.Trim().Length > 0)
            profile.BirthDate = DateTime.Parse(txtBirthDate.Text);
        profile.Occupation = ddlOccupations.SelectedValue;
        profile.Website = txtWebsite.Text;
        profile.Address.Street = txtStreet.Text;
        profile.Address.City = txtCity.Text;
        profile.Address.PostalCode = txtPostalCode.Text;
        profile.Address.County = txtCounty.Text;
        profile.Contacts.Phone = txtPhone.Text;
        profile.Contacts.Fax = txtFax.Text;
        profile.Contacts.Mobile = txtMobile.Text;
        profile.Save();
    }

    private void ImageUpLoad()
    {

        // generate random filename
        string rndFileName = Guid.NewGuid().ToString("N") + ".png";
        //string imgName = FileUpload1.FileName.ToString();

        //sets the image path
        string imgPath = "~/Images/ProfileImages/" + rndFileName;

        //then save it to the Folder
        FileUpload1.SaveAs(Server.MapPath(imgPath));

        //get the size in bytes that
        int imgSize = FileUpload1.PostedFile.ContentLength;

        //validates the posted file before saving
        if (FileUpload1.PostedFile != null && FileUpload1.PostedFile.FileName != "")
        {

            if (FileUpload1.PostedFile.ContentLength > 5120000) // 5120 KB means 5MB
            {
                Page.ClientScript.RegisterClientScriptBlock(typeof(Page), "Figyelmeztetés", "alert('A feltölteni kívánt fájl mérete túl nagy!')", true);
            }

            else
            {
                // Store image data into user's profile
                ProfileCommon p = this.Profile.GetProfile(this.UserName);
                p.Picture.ImageName = rndFileName;
                p.Picture.ImageSize = imgSize;
                p.Picture.ImagePath = imgPath;
                p.Save();

                Response.Write("Új fénykép sikeresen elmentve!");
            }
        }
    }


    protected void UploadButton_Click(object sender, EventArgs e)
    {
        ImageUpLoad();
    }

    protected void btnUpdateProfile_Click(object sender, EventArgs e)
    {
        Save();
        lblProfileOK.Visible = true;
    }
}