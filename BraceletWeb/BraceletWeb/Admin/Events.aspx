<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Events.aspx.cs" Inherits="Events" %>
<%@ Register Src="../Members/Controls/UserEvents.ascx" TagName="UserEvents" TagPrefix="mb" %>
<%@ register assembly="GMaps" namespace="Subgurim.Controles" tagprefix="gm" %>


<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2><asp:Label ID="LabelHeader" runat="server" /></h2>
    <br />
    <!--Tolerances UserControl-->
    <mb:UserEvents ID="eventControl" runat="server" />

</asp:Content>