<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Admin.aspx.cs" Culture="en-GB" Inherits="Admin_Admin" %>
<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="ajaxToolkit" %>

<asp:Content ID="Content1" ContentPlaceHolderID="HeadContent" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" Runat="Server">
    <h2>
        Üdv az adminisztrációs felületen!
    </h2>

    <div>Felhasználók kezelése</div>
    <p></p>
    <b>- Az összes regisztrált felhasználó: <asp:Literal runat="server" ID="lblTotalUsers" /><br />
        - Az ebben a pillanatban belépett felhasználók: <asp:Literal runat="server" ID="lblOnlineUsers" /></b>
    <p></p>
    A felhasználók kezdőbetű szerinti szűréséhez kattintson a kívánt betűre:
    <p></p>
    
    <asp:Repeater runat="server" ID="rptAlphabetBar"
        OnItemCommand="rptAlphabetBar_ItemCommand">
        <ItemTemplate><asp:LinkButton ID="LinkButton1" runat="server" Text='<%# Container.DataItem %>'
            CommandArgument='<%# Container.DataItem %>' />&nbsp;&nbsp;
        </ItemTemplate>
    </asp:Repeater>
    
    <p></p>
    <br />
    A következő mezők segítségével felhasználónév és e-mail cím részletre kereshet:
    <p></p>
    
    <asp:UpdatePanel ID="AdminPanel" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <asp:DropDownList runat="server" ID="ddlUserSearchTypes" AutoPostBack="True" 
                onselectedindexchanged="ddlUserSearchTypes_SelectedIndexChanged">
                <asp:ListItem Text="Felhasználónév" Selected="true" />
                <asp:ListItem Text="E-mail" />
            </asp:DropDownList>
            tartalmazza:
            <asp:TextBox runat="server" ID="txtSearchText" />
            <ajaxToolkit:AutoCompleteExtender 
                runat="server" 
                ID="autoComplete1"
                TargetControlID="txtSearchText"
                ServiceMethod="GetCompletionList"
                UseContextKey="true"
                MinimumPrefixLength="2" 
                EnableCaching="true"
                CompletionListCssClass="autocomplete_completionListElement" 
                CompletionListItemCssClass="autocomplete_listItem" 
                CompletionListHighlightedItemCssClass="autocomplete_highlightedListItem"
                CompletionInterval="500" CompletionSetCount="10"  
                DelimiterCharacters=";, :"
                ShowOnlyCurrentWordInCompletionListItem="true">
            </ajaxToolkit:AutoCompleteExtender>
            <asp:Button runat="server" ID="btnSearch" Text="Keresés"
                OnClick="btnSearch_Click" />

            <asp:HiddenField ID="HiddenField1" runat="server" />
            <ajaxToolkit:ModalPopupExtender id="ModalPopupExtender1" PopupControlID="PanelPopup" runat="server" 
                OkControlID="ButtonPopupOK" TargetControlID="HiddenField1" Enabled="False">
                <Animations>
                <OnShown>
                    <FadeIn Duration=".75" Fps="20" />
                </OnShown>
            </Animations>
            </ajaxToolkit:ModalPopupExtender>
            <asp:Panel ID="PanelPopup" runat="server" Height="100px" BackColor="White" 
                Width="300px" style="display:none; padding:5px; border-style:solid; border-width:thin;" 
                HorizontalAlign="Center" >
                <asp:Label ID="LabelPopupMessage" BorderWidth="0px" runat="server" />
                <br />
                <asp:Button ID="ButtonPopupOK" runat="server" Text="Rendben" Width="70px" style="position:absolute; left:45%; bottom:10%;" />
            </asp:Panel>

            <asp:GridView ID="gvUsers" runat="server" AutoGenerateColumns="false" DataKeyNames="UserName"
                OnRowCreated="gvUsers_RowCreated" OnRowDeleting="gvUsers_RowDeleting" OnRowCommand="OnRowCommand">
                <Columns>
                  <asp:BoundField HeaderText="Felhasználónév" DataField="UserName" />
                  <asp:HyperLinkField HeaderText="E-mail cím" DataTextField="Email"
                     DataNavigateUrlFormatString="mailto:{0}" DataNavigateUrlFields="Email" />
                  <asp:BoundField HeaderText="Létrehozva" DataField="CreationDate"
                     DataFormatString="{0:yy/MM/dd h:mm tt}" />
                  <asp:BoundField HeaderText="Utolsó aktivitás" DataField="LastActivityDate"
                     DataFormatString="{0:yy/MM/dd h:mm tt}" />
                  <asp:CheckBoxField HeaderText="Engedélyezett?" DataField="IsApproved"
                     HeaderStyle-HorizontalAlign="Center" ItemStyle-HorizontalAlign="Center" />
                  <asp:HyperLinkField Text="<img src='../images/tolerance.png' border='0' />"
                     DataNavigateUrlFormatString="Tolerances.aspx?UserName={0}"
                     DataNavigateUrlFields="UserName" />
                  <asp:HyperLinkField Text="<img src='../images/events.png' border='0' />"
                     DataNavigateUrlFormatString="Events.aspx?UserName={0}"
                     DataNavigateUrlFields="UserName" />
                  <asp:HyperLinkField Text="<img src='../images/chart.png' border='0' />"
                     DataNavigateUrlFormatString="Measures.aspx?UserName={0}"
                     DataNavigateUrlFields="UserName" />
                  <asp:ButtonField CommandName="ForceMeasure" ButtonType="Image"
                     ImageUrl="~/images/record.png" />
                  <asp:HyperLinkField Text="<img src='../images/edit.png' border='0' />"
                     DataNavigateUrlFormatString="EditUsers.aspx?UserName={0}"
                     DataNavigateUrlFields="UserName" />
                  <asp:ButtonField CommandName="Delete" ButtonType="Image"
                     ImageUrl="~/images/delete.png" />
               </Columns>
               <EmptyDataTemplate>Nem található a keresési feltételnek megfelelő felhasználó.</EmptyDataTemplate>
           </asp:GridView>
        </ContentTemplate>
        <Triggers>
        </Triggers>
    </asp:UpdatePanel>

</asp:Content>