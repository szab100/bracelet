<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Members.aspx.cs" Inherits="Members_Members" %>
<%@ Register Src="../Members/Controls/UserProfile.ascx" TagName="UserProfile" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h1>
        Üdv a regisztráltak oldalain!
    </h1>
    <asp:HyperLink ID="ChangePasswordHyperLink" runat="server" EnableViewState="false">
        <asp:Image Width="35px" runat="server" BorderStyle="None" ImageUrl="~/Images/keys.png" />
        <br />
        <asp:Label runat="server" Text="Jelszócsere" />
    </asp:HyperLink>
    <br />
    <br />
    
    <!-- User Profile control -->
    <mb:UserProfile ID="UserProfile1" runat="server" />
        
</asp:Content>

