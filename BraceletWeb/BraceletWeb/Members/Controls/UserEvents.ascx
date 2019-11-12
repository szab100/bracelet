<%@ Control Language="C#" AutoEventWireup="true" CodeFile="UserEvents.ascx.cs" Inherits="UserEvents" %>
<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="ajaxToolkit" %>
<%@ register assembly="GMaps" namespace="Subgurim.Controles" tagprefix="gm" %>

<script type="text/javascript" language="javascript">
    
    function clearText(ctrl, defaultText) {
        if (ctrl.value == defaultText)
            ctrl.value = ""
        ctrl.style.color = "#000000";
    }
    function resetText(ctrl, defaultText) {
        if (ctrl.value == "") {
            ctrl.value = defaultText
            ctrl.style.color = "#C0C0C0";
        }
    }

</script>

<asp:EntityDataSource ID="EventsDataSource" runat="server" 
    ConnectionString="name=braceletEntities" 
    DefaultContainerName="braceletEntities" EnableFlattening="False" 
    EntitySetName="Events" Include="aspnet_Users" >
</asp:EntityDataSource>

<asp:QueryExtender ID="QueryExtender1" runat="server" TargetControlID="EventsDatasource">

    <%-- Filter by User --%>
    <asp:CustomExpression OnQuerying="FilterEventsByUser">
        <asp:ControlParameter Name="username" ControlID="TextBoxUserName" PropertyName="Text" />
    </asp:CustomExpression>

    <%-- Filter by Range in Selected column --%>
    <asp:CustomExpression OnQuerying="FilterEventsByRange" >
        <asp:ControlParameter Name="rangeFrom" ControlID="TextBoxRangeFrom" PropertyName="Text" />
        <asp:ControlParameter Name="rangeTo" ControlID="TextBoxRangeTo" PropertyName="Text" />
        <asp:ControlParameter Name="enabled" ControlID="CheckBoxRangeFilter" PropertyName="Checked" />
    </asp:CustomExpression>

    <asp:RangeExpression DataField="Raised" MinType="Inclusive" MaxType="Inclusive">
        <asp:ControlParameter  ControlID="TextBoxDateFrom" />
        <asp:ControlParameter  ControlID="TextBoxDateTo" />
    </asp:RangeExpression>

</asp:QueryExtender>

    <!-- Filter Panel -->
    <asp:UpdatePanel ID="FilterPanel" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <fieldset style="width:99%">
            <legend>Szűrési feltételek</legend>
                <asp:Table runat="server" ID="FilterTable">

                <asp:TableRow>
                        <asp:TableCell>
                            <!-- User Filter -->
                            <asp:CheckBox ID="CheckBoxUserFilter" runat="server" AutoPostBack="true"
                                Text="Felhasználó" TextAlign="Right" OnCheckedChanged="FilterCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="UserFilterHolder" runat="server">
                                <asp:TextBox ID="TextBoxUserName" runat="server" Width="100" />
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>

                    <asp:TableRow>
                        <asp:TableCell>
                            <!-- Range Filter -->
                            <asp:CheckBox ID="CheckBoxRangeFilter" runat="server" AutoPostBack="true"
                                Text="Szint" TextAlign="Right" OnCheckedChanged="FilterCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="RangeFilterHolder" runat="server">
                                <asp:TextBox ID="TextBoxRangeFrom" runat="server" Width="60" />
                                -tól&nbsp;&nbsp;
                                <asp:TextBox ID="TextBoxRangeTo" runat="server" Width="60" />
                                -ig
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>

                    <asp:TableRow>
                        <asp:TableCell>
                            <!-- Date Filter -->
                            <asp:CheckBox ID="CheckBoxDateFilter" runat="server" AutoPostBack="true" 
                                Text="Időszak" TextAlign="Right" OnCheckedChanged="FilterCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="DateFilterHolder" runat="server">
                                <asp:TextBox ID="TextBoxDateFrom" runat="server" Width="100" AutoPostBack="true" />&nbsp;
                                <asp:Image runat="server" AlternateText="Icon" ImageUrl="~/Images/calendar.jpg" ID="ImageCalendarFrom" />
                                <ajaxToolkit:CalendarExtender ID="CalendarExtenderFrom" runat="server" Format="yyyy.MM.dd."
                                    TargetControlID="TextBoxDateFrom" PopupButtonID="ImageCalendarFrom" />
                            
                                &nbsp; - &nbsp;
                            
                                <asp:TextBox ID="TextBoxDateTo" runat="server" Width="100" AutoPostBack="true" />&nbsp;
                                <asp:Image runat="server" AlternateText="Icon" ImageUrl="~/Images/calendar.jpg" ID="ImageCalendarTo" />
                                <ajaxToolkit:CalendarExtender ID="CalendarExtenderTo" runat="server" Format="yyyy.MM.dd."
                                    TargetControlID="TextBoxDateTo" PopupButtonID="ImageCalendarTo" />        
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>
                    <asp:TableRow>
                        <asp:TableCell>
                            <!-- Search button -->
                            <asp:Button ID="ButtonFilter" runat="server" Text="Szűrés" />
                        </asp:TableCell>
                    </asp:TableRow>
                    
                </asp:Table>
            </fieldset>
        </ContentTemplate>
        <Triggers>           
        </Triggers>
    </asp:UpdatePanel>

<br />
    
<asp:UpdatePanel ID="GridViewPanel" Clientidmode="Static" runat="server" UpdateMode="Conditional">
    <ContentTemplate>

        <!-- Selected event's location -->
        <asp:PlaceHolder ID="GMapHolder" runat="server" Visible="false">
            <p><b>A kiválasztott esemény pozíciója</b></p>
            <gm:GMap ID="GMap" runat="server" BorderStyle="Solid" BorderWidth="4px" BorderColor="#333333" Width="100%" />
            <br />
        </asp:PlaceHolder>

        <p><b>A keresési feltételeknek megfelelő események:</b></p>

        <asp:GridView ID="GridViewEvents" runat="server" OnRowDataBound="GridViewEvents_RowDataBound" 
            AllowPaging="True" Width="100%" AllowSorting="True" AutoGenerateColumns="False" CellPadding="4" 
            DataKeyNames="ID" DataSourceID="EventsDataSource" ForeColor="#333333" OnRowCommand="GridViewEvents_OnRowCommand"
            GridLines="None" BorderStyle="Solid" BorderWidth="1px" SelectedRowStyle-CssClass="SelectedRowStyle">
            <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
            <Columns>
                <asp:TemplateField HeaderText="Felhasználó">
                    <ItemTemplate>
                        <asp:Label ID="Label1" runat="server" Text='<%# Eval("aspnet_Users.UserName") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Eseménykód">
                    <ItemTemplate>
                        <asp:Label ID="LabelEventCode" runat="server" Text='<%# GetEventCode( Eval("EventCode")) %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="Level" HeaderText="Szint" SortExpression="Level" />
                <asp:BoundField DataField="Raised" HeaderText="Esemény dátuma" SortExpression="Raised" />
                <asp:BoundField DataField="Description" HeaderText="Leírás" ItemStyle-Width="200px" SortExpression="Description" />
                <asp:TemplateField HeaderText="Státusz">
                    <ItemTemplate>
                        <asp:Label ID="LabelStatus" runat="server" Text='<%# GetStatus( Eval("Status")) %>' />
                        <asp:Image ID="ImageNote" runat="server" ToolTip='<%# Eval("Note") %>' ImageUrl="~/Images/info.png" />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Pozíció">
                    <ItemTemplate>
                        <center>
                            <asp:ImageButton ID="ButtonPosition" runat="server" Text="Megtekintés" ImageUrl="~/Images/map.png" CommandName="Position" CommandArgument="<%# ((GridViewRow) Container).RowIndex %>" />
                        </center>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Felvétel">
                    <ItemTemplate>
                            <asp:ImageButton ID="ButtonDownloadRec" runat="server" Text="Letöltés" ImageUrl="~/Images/save.png" CommandName="DownloadRecording" CommandArgument="<%# ((GridViewRow) Container).RowIndex %>" Visible='<%# GetRecButtonVisible(Eval("ID")) %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Nyugtázás" ItemStyle-Width="170px">
                    <ItemTemplate>
                        <asp:TextBox ID="TextBoxHandle" runat="server" Width="115" MaxLength="100" />
                        <asp:ImageButton ID="ButtonHandle" runat="server" Text="Ok" ImageUrl="~/Images/ok.png" CommandName="Handle" CommandArgument="<%# ((GridViewRow) Container).RowIndex %>" />
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>
            <EditRowStyle BackColor="#999999" />
            <FooterStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <HeaderStyle BackColor="#5D7B9D" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="#284775" ForeColor="White" HorizontalAlign="Center" />
            <RowStyle BackColor="#F7F6F3" ForeColor="#333333" />
            <SelectedRowStyle BackColor="#E2DED6" Font-Bold="True" ForeColor="#333333" />
            <SortedAscendingCellStyle BackColor="#E9E7E2" />
            <SortedAscendingHeaderStyle BackColor="#506C8C" />
            <SortedDescendingCellStyle BackColor="#FFFDF8" />
            <SortedDescendingHeaderStyle BackColor="#6F8DAE" />
            
            <EmptyDataTemplate>
             A megadott keresési feltételeknek egyetlen esemény sem felelt meg.
            </EmptyDataTemplate>
        </asp:GridView>
        <br />

    </ContentTemplate>
    <Triggers>
        <asp:AsyncPostBackTrigger ControlID="ButtonFilter" EventName="Click" />
    </Triggers>

</asp:UpdatePanel>