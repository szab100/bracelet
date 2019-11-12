using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Security;
using braceletModel;
using System.IO;
using System.Runtime.Serialization;

/// <summary>
/// Summary description for BraceletMobileService
/// </summary>
[WebService(Namespace = "http://soap.bracelet.symlink.hu/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class BraceletMobileService : System.Web.Services.WebService
{

    public BraceletMobileService()
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    public string HelloWorld()
    {
        return "Hello World";
    }

    [WebMethod]
    public bool Login(Credentials credentials)
    {
        return Membership.ValidateUser(credentials.UserName, credentials.Password);
    }

    [WebMethod]
    public bool StoreMeasure(MobileMeasure p_measure, Credentials credentials)
    {
        // If credentials not valid --> return false
        if (!Membership.ValidateUser(credentials.UserName, credentials.Password)) return false;

        // If we are here, credentials are fine, let's store this measure
        try
        {
            Measure measure = new Measure();

            measure.Acceleration = p_measure.Acceleration;
            measure.BloodOxygen = p_measure.BloodOxygen;
            measure.Pulse = p_measure.Pulse;
            measure.Temperature = p_measure.Temperature;
            measure.SensorID = p_measure.SensorID;

            // Try to get measured date
            if (p_measure.Measured != null) measure.Measured = p_measure.Measured;
            else measure.Measured = DateTime.Now;

            // Set missing values
            measure.UserID = (Guid)Membership.GetUser(credentials.UserName).ProviderUserKey;
            measure.Received = DateTime.Now;

            // Store
            using (braceletEntities context = new braceletEntities())
            {
                context.Measures.AddObject(measure);
                context.SaveChanges();
            }

            return true;
        }
        catch (Exception)
        {
            // If something went wrong..
            return false;
        }

    }

    [WebMethod]
    public MobileTolerance GetTolerance(Credentials credentials)
    {
        // If credentials not valid --> return false
        if (!Membership.ValidateUser(credentials.UserName, credentials.Password)) return null;

        // If we are here, credentials are fine, let's return his f**in tolerances..
        using (braceletEntities context = new braceletEntities())
        {
            Tolerance t = context.Tolerances.Where(o => o.aspnet_Users.UserName == credentials.UserName).FirstOrDefault();
            MobileTolerance mt = new MobileTolerance();

            if (t.AccelerationTolerance.HasValue) mt.AccelerationMax = t.AccelerationTolerance.Value;
            if (t.BloodOxygenToleranceHigh.HasValue) mt.BloodOxygenMax = t.BloodOxygenToleranceHigh.Value;
            if (t.BloodOxygenToleranceLow.HasValue) mt.BloodOxygenMin = t.BloodOxygenToleranceLow.Value;
            if (t.PulseToleranceHigh.HasValue) mt.PulseMax = t.PulseToleranceHigh.Value;
            if (t.PulseToleranceLow.HasValue) mt.PulseMin = t.PulseToleranceLow.Value;
            if (t.TempToleranceHigh.HasValue) mt.TempMax = t.TempToleranceHigh.Value;
            if (t.TempToleranceLow.HasValue) mt.TempMin = t.TempToleranceLow.Value;

            return mt;
        }
    }

    [WebMethod]
    public int RaiseEvent(MobileEvent mobEvent, Credentials credentials)
    {
        // If credentials not valid --> return false
        if (!Membership.ValidateUser(credentials.UserName, credentials.Password)) return -1;

        try
        {
            // Create new Event
            Event newEvent = new Event();
            newEvent.EventCode = (short)mobEvent.EventCode;
            newEvent.Description = mobEvent.Description;
            newEvent.Level = (short)mobEvent.Level;
            newEvent.PositionLatitude = mobEvent.Latitude;
            newEvent.PositionLongitude = mobEvent.Longitude;

            // Set missing values
            newEvent.UserID = (Guid)Membership.GetUser(credentials.UserName).ProviderUserKey;
            newEvent.Raised = DateTime.Now;

            // If we are here, credentials are fine, let's store this event
            using (braceletEntities context = new braceletEntities())
            {
                // Store
                newEvent.Status = (int)CommonNames.EventStatus.New;
                context.Events.AddObject(newEvent);

                context.SaveChanges();

                // Return OK
                return newEvent.ID;
            }
        }
        catch (Exception)
        {
            // If something went wrong
            return -1;
        }
    }

    [WebMethod]
    public bool UploadRecording(UploadFile file, int eventID, Credentials credentials)
    {
        // If credentials not valid --> return false
        if (!Membership.ValidateUser(credentials.UserName, credentials.Password)) return false;

        try
        {
            // Save file
            String fileName = credentials.UserName + "-" + file.FileName;
            FileStream FileStream = new FileStream("C:\\BraceletRecordings\\" + fileName, FileMode.Create);
            byte[] fileBytes = Convert.FromBase64String(file.File);
            FileStream.Write(fileBytes, 0, fileBytes.Length);
            FileStream.Close();
            FileStream.Dispose();

            // Insert Recording
            using (braceletEntities context = new braceletEntities())
            {
                Recording r = new Recording();

                Event e = context.Events.Where(o => o.ID == eventID).FirstOrDefault();
                r.FileName = fileName;
                if (e != null) r.Event = e;
                r.Uploaded = DateTime.Now;
                r.UserID = (Guid)Membership.GetUser(credentials.UserName).ProviderUserKey;

                context.Recordings.AddObject(r);
                context.SaveChanges();

                // Everything is OK
                return true;
            }
        }
        catch (Exception)
        {
            // Something went wrong..
            return false;
        }
    }

    [WebMethod]
    public int IsActionQueued(Credentials credentials)
    {

        // If credentials not valid --> return false
        if (!Membership.ValidateUser(credentials.UserName, credentials.Password)) return -1;

        try
        {
            using (braceletEntities context = new braceletEntities())
            {
                // Get uncompleted actions
                ActionQueue q = context.ActionQueues.Where(o => o.aspnet_Users.UserName == credentials.UserName && (CommonNames.ActionStatus) o.Status == CommonNames.ActionStatus.New ).FirstOrDefault();

                // There's a queue
                if (q != null)
                {

                    // Set completed
                    q.Completed = DateTime.Now;
                    q.Status = (short) CommonNames.ActionStatus.Completed;

                    // Save changes to DB
                    context.SaveChanges();

                    // Return proper value (by action)
                    switch ((CommonNames.ActionType) q.Action)
                    {
                        case CommonNames.ActionType.ForceMeasure:
                            return (int) CommonNames.ActionType.ForceMeasure;

                        default:
                            return 0;
                    }
                }
            }

            // Nothing to do
            return -1;
        }
        catch (Exception e)
        {
            return -1;
        }

    }

    #region DataMembers
    [DataContract]
    public class UploadFile
    {
        [DataMember]
        public string FileName;

        [DataMember]
        public String File;
    }

    [DataContract]
    public class Credentials
    {
        String _username;
        String _password;

        [DataMember]
        public String UserName
        {
            get { return _username; }
            set { _username = value; }
        }

        [DataMember]
        public String Password
        {
            get { return _password; }
            set { _password = value; }
        }
    }

    [DataContract]
    public class MobileMeasure
    {
        [DataMember]
        public int Pulse
        {
            get;
            set;
        }

        [DataMember]
        public double Temperature
        {
            get;
            set;
        }

        [DataMember]
        public double Acceleration
        {
            get;
            set;
        }

        [DataMember]
        public double BloodOxygen
        {
            get;
            set;
        }

        [DataMember]
        public DateTime Measured
        {
            get;
            set;
        }

        [DataMember]
        public String SensorID
        {
            get;
            set;
        }

    }

    [DataContract]
    public class MobileTolerance
    {
        [DataMember]
        public int PulseMin
        {
            get;
            set;
        }

        [DataMember]
        public int PulseMax
        {
            get;
            set;
        }

        [DataMember]
        public double TempMin
        {
            get;
            set;
        }

        [DataMember]
        public double TempMax
        {
            get;
            set;
        }

        [DataMember]
        public double AccelerationMax
        {
            get;
            set;
        }

        [DataMember]
        public double BloodOxygenMin
        {
            get;
            set;
        }

        [DataMember]
        public double BloodOxygenMax
        {
            get;
            set;
        }
    }

    [DataContract]
    public class MobileEvent
    {
        [DataMember]
        public int EventCode
        {
            get;
            set;
        }

        [DataMember]
        public int Level
        {
            get;
            set;
        }

        [DataMember]
        public string Description
        {
            get;
            set;
        }

        [DataMember]
        public double Longitude
        {
            get;
            set;
        }

        [DataMember]
        public double Latitude
        {
            get;
            set;
        }
    }
    #endregion

}
