<%@ WebHandler Language="C#" Class="RecordingHandler" %>

using System;
using System.Web;


public class RecordingHandler : IHttpHandler
{

    public void ProcessRequest(HttpContext context)
    {

        if (context.User.IsInRole("Administrator"))
        {

            context.Response.Buffer = true;
            context.Response.Clear();
            context.Response.AddHeader("content-disposition", context.Request.Url.AbsolutePath);
            context.Response.ContentType = "audio/mpeg";
            context.Response.WriteFile(context.Server.MapPath(context.Request.Url.AbsolutePath));
        }
        else
        {
            context.Response.Write("Only administrators can access recordings. Sorry");
        }



    }

    public override string ToString()
    {
        return "Bracelet recordings download handler.";
    }

    public bool IsReusable
    {
        get
        {
            return false;
        }
    }

}
