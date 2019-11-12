<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Events.aspx.cs" Inherits="Events" %>
<%@ Register Src="../Members/Controls/UserEvents.ascx" TagName="UserEvents" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Az Ön rögzített eseményei</h2>
    <br />
    <!--Tolerances UserControl-->
    <mb:UserEvents ID="eventControl" runat="server" />
</asp:Content>