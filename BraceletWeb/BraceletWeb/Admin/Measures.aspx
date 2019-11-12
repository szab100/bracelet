<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Measures.aspx.cs" Inherits="Measures" %>
<%@ Register Src="../Members/Controls/UserMeasures.ascx" TagName="UserMeasures" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2><asp:Label ID="LabelHeader" runat="server" /></h2>
    <br />
    <!--Measures UserControl-->
    <mb:UserMeasures ID="measuresControlAdmin" runat="server" />
</asp:Content>