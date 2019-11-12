<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Tolerances.aspx.cs" Inherits="Tolerances" %>
<%@ Register Src="../Members/Controls/UserTolerances.ascx" TagName="UserTolerances" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2><asp:Label ID="LabelHeader" runat="server" /></h2>
    <br />
    <!--Measures UserControl-->
    <mb:UserTolerances ID="tolerancesControlAdmin" runat="server" />
</asp:Content>