<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Measures.aspx.cs" Inherits="Measures" %>
<%@ Register Src="../Members/Controls/UserMeasures.ascx" TagName="UserMeasures" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>Az Ön mérései</h2>
    <br />
    <!--Measures UserControl-->
    <mb:UserMeasures ID="measuresControl1" runat="server" />
</asp:Content>