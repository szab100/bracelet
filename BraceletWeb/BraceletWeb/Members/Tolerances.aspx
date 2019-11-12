<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Tolerances.aspx.cs" Inherits="Members_Tolerances" %>
<%@ Register Src="../Members/Controls/UserTolerances.ascx" TagName="UserTolerances" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Az Ön tolerancia beállításai</h2>
    <br />
    <!--Tolerances UserControl-->
    <mb:UserTolerances ID="toleranceControl1" runat="server" />
</asp:Content>