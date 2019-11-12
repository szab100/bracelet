<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="EditUsers.aspx.cs" Inherits="Admin_EditUsers" %>
<%@ Register Src="../Members/Controls/UserProfile.ascx" TagName="UserProfile" TagPrefix="mb" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">

<fieldset style="width:50%">
    <legend>Felhasználói adatok</legend>
<p></p>
<table cellpadding="2">
   <tr>
      <td width="130">Felhasználónév:</td>
      <td width="300"><asp:Literal runat="server" ID="lblUserName" /></td>
   </tr>
   <tr>
      <td>E-mail cím:</td>
      <td><asp:HyperLink ID="lnkEmailAddress" runat="server" /></td>
   </tr>
   <tr>
      <td>Regisztrálva:</td>
      <td><asp:Literal  ID="lblRegistered" runat="server" /></td>
   </tr>
   <tr>
      <td>Utolsó belépés:</td>
      <td><asp:Literal ID="lblLastLogin" runat="server" /></td>
   </tr>
   <tr>
      <td>Utolsó aktivitás:</td>
      <td><asp:Literal ID="lblLastActivity" runat="server" /></td>
   </tr>
   <tr>
      <td>Most Online:</td>
      <td><asp:CheckBox ID="chkIsOnlineNow" Enabled="false" runat="server" /></td>
   </tr>
   <tr>
      <td>Engedélyezett:</td>
      <td><asp:CheckBox ID="chkIsApproved" AutoPostBack="true" OnCheckedChanged="chkIsApproved_CheckedChanged" runat="server"/></td>
   </tr>
   <tr>
      <td>Kizárva:</td>
      <td><asp:CheckBox ID="chkIsLockedOut" AutoPostBack="true" OnCheckedChanged="chkIsLockedOut_CheckedChanged" runat="server"/></td>
   </tr>
</table>
</fieldset>
<p></p>

<fieldset style="width:50%">
    <legend>Jelszó változtatás</legend>
<p></p>
<table cellpadding="2">
   <tr>
      <td width="130">Új jelszó:</td>
      <td width="300">
        <asp:TextBox runat="server" ID="textBoxNewPassword" TextMode="Password" Width="100"/> 
        <asp:RequiredFieldValidator ID="RequiredFieldValidatorChPass"
                    ControlToValidate="textBoxNewPassword" SetFocusOnError="true" ErrorMessage="Megadása kötelező!" ValidationGroup="ChangePass" runat="server" ForeColor="Red" />
      </td>
   </tr>
    <tr>
      <td width="130">Új jelszó (ismét):</td>
      <td width="300">
        <asp:TextBox runat="server" ID="textBoxNewPasswordVerify" TextMode="Password" Width="100"/>
        <asp:RequiredFieldValidator ID="RequiredFieldValidatorChPassVerify"
                ControlToValidate="textBoxNewPasswordVerify" SetFocusOnError="true" ErrorMessage="Megadása kötelező!" ValidationGroup="ChangePass" runat="server" ForeColor="Red" />
      </td>
   </tr>
   <tr>
        <td colspan="2" align="right">
            <asp:CompareValidator id="ComparePasswordValidator" runat="server" ErrorMessage="A megadott jelszavak nem egyeznek!" ControlToValidate="textBoxNewPassword" 
                ControlToCompare="textBoxNewPasswordVerify" ValidationGroup="ChangePass" ForeColor="Red" />
            <asp:Button runat="server" ID="ButtonChPass" Text="Megváltoztat" ValidationGroup="ChangePass" OnClick="buttonChPass_Click" />
        </td>
   </tr>
   </table>
</fieldset>

<p></p>

<fieldset style="width:50%">
    <legend>Szerepek</legend>
    <p></p>
    <div ><b>Aktív szerepek:</b></div>
    <p></p>
    <table cellpadding="2" width="97%">
       <tr>
        <td colspan="2"><asp:CheckBoxList runat="server" ID="chklRoles" RepeatColumns="5" CellSpacing="4" /></td>
       </tr>
       <tr>
        <td>&nbsp</td>
        <td align="right">
          <asp:Label runat="server" ID="lblRolesOK" Text="A szerepek frissítésre kerültek!" Visible="false" />
          <asp:Button ID="btnUpdate" Text="Érvényesít" OnClick="btnUpdate_Click" runat="server"/>
        </td>
       </tr>
    </table>
    <p></p>
    <div ><b>Új szerep:</b></div>
    <p></p>
    <table width="97%">
        <tr>
            <td align="right">
                Szerepnév: <asp:TextBox ID="txtNewRole" runat="server" Width="200"/>
                <asp:RequiredFieldValidator ID="validatorRequireRole"
                    ControlToValidate="txtNewRole" SetFocusOnError="true"
                    ErrorMessage="Szerepnév megadása kötelező!" ValidationGroup="CreateRole" runat="server">
                </asp:RequiredFieldValidator>
                <asp:Button runat="server" ID="btnCreate" Text="Létrehoz" ValidationGroup="CreateRole" OnClick="btnCreate_Click" />
            </td>
        </tr>
    </table>
</fieldset>
<p></p>
<!-- User Profile control -->
<mb:UserProfile ID="UserProfile1" runat="server" />

</asp:Content>