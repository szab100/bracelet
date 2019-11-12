<%@ Control Language="C#" AutoEventWireup="true" CodeFile="UserMeasures.ascx.cs" Inherits="UserMeasures" %>
<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="ajaxToolkit" %>

<asp:EntityDataSource ID="MeasuresDataSource" runat="server" 
    ConnectionString="name=braceletEntities" 
    DefaultContainerName="braceletEntities" EnableFlattening="False" 
    EntitySetName="Measures" Include="aspnet_Users" >
</asp:EntityDataSource>

<asp:QueryExtender ID="QueryExtender1" runat="server" TargetControlID="MeasuresDatasource">

    <%-- Filter by User --%>
    <asp:CustomExpression OnQuerying="FilterMeasuresByUser">
        <asp:ControlParameter Name="username" ControlID="TextBoxUserName" PropertyName="Text" />
    </asp:CustomExpression>

    <%-- Filter by Range in Selected column --%>
    <asp:CustomExpression OnQuerying="FilterMeasuresByRange" >
        <asp:ControlParameter Name="column" ControlID="DropDownListRangeColumn" PropertyName="SelectedValue" />
        <asp:ControlParameter Name="rangeFrom" ControlID="TextBoxRangeFrom" PropertyName="Text" />
        <asp:ControlParameter Name="rangeTo" ControlID="TextBoxRangeTo" PropertyName="Text" />
        <asp:ControlParameter Name="enabled" ControlID="CheckBoxRangeFilter" PropertyName="Checked" />
    </asp:CustomExpression>

    <asp:RangeExpression DataField="Measured" MinType="Inclusive" MaxType="Inclusive">
        <asp:ControlParameter  ControlID="TextBoxDateFrom" />
        <asp:ControlParameter  ControlID="TextBoxDateTo" />
    </asp:RangeExpression>

</asp:QueryExtender>

    <!-- Filter Panel -->
    <asp:UpdatePanel ID="FilterPanel" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <fieldset style="width:650px">
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
                                Text="Tartomány" TextAlign="Right" OnCheckedChanged="FilterCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="RangeFilterHolder" runat="server">
                                <asp:DropDownList ID="DropDownListRangeColumn" runat="server" AutoPostBack="true">
                                    <asp:ListItem Text="- Válasszon -" Value="" />
                                    <asp:ListItem Text="Gyorsulás" Value="acc" />
                                    <asp:ListItem Text="Pulzus" Value="pulse" />
                                    <asp:ListItem Text="Testhőmérséklet" Value="temp" />
                                    <asp:ListItem Text="Véroxigén" Value="blox" />
                                </asp:DropDownList>

                                &nbsp;értéke: &nbsp;&nbsp;
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
                                Text="Mérési időszak" TextAlign="Right" OnCheckedChanged="FilterCheckBox_CheckedChanged" />
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
    
<p><b>A keresési feltételeknek megfelelő mérések:</b></p>
<asp:UpdatePanel ID="GridViewPanel" Clientidmode="Static" runat="server" UpdateMode="Conditional">
    <ContentTemplate>
        <asp:GridView ID="GridViewMeasures" runat="server" AllowPaging="True" Width="660"
            AllowSorting="True" AutoGenerateColumns="False" CellPadding="4" 
            DataKeyNames="ID" DataSourceID="MeasuresDataSource" ForeColor="#333333" 
            GridLines="None" BorderStyle="Solid" BorderWidth="1px">
            <AlternatingRowStyle BackColor="White" ForeColor="#284775" />
            <Columns>
                <asp:TemplateField HeaderText="Felhasználó">
                    <ItemTemplate>
                        <asp:Label ID="Label1" runat="server" Text='<%# Eval("aspnet_Users.UserName") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="Pulse" HeaderText="Pulzus" SortExpression="Pulse" />
                <asp:BoundField DataField="Temperature" HeaderText="Testhőmérséklet" 
                    SortExpression="Temperature" DataFormatString="{0:0.## °C}" />
                <asp:BoundField DataField="Acceleration" HeaderText="Gyorsulás" 
                    SortExpression="Acceleration" DataFormatString="{0:0.## G}" />
                <asp:BoundField DataField="BloodOxygen" HeaderText="Véroxigén" 
                    SortExpression="BloodOxygen" DataFormatString="{0:0.## '%'}" />
                <asp:BoundField DataField="Measured" HeaderText="Mérés dátuma" 
                    SortExpression="Measured" />
                <asp:BoundField DataField="Received" HeaderText="Jelzés dátuma" 
                    SortExpression="Received" />
                <asp:BoundField DataField="SensorID" HeaderText="Szenzor MAC" 
                    SortExpression="SensorID" />
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
             A megadott keresési feltételeknek egyetlen mérés sem felelt meg.
            </EmptyDataTemplate>
        </asp:GridView>
        <br />
        
        <%--Postback from CP--%>
        <asp:hiddenfield id="Hiddenfield1" runat="Server"></asp:hiddenfield>
        
        <script type='text/javascript'>
            function colorChanged(sender) {
                __doPostBack('GridViewPanel', '');
            }
        </script>

        <br />
        <fieldset style="width:650px">
            <legend>Grafikon nézet</legend>
                <asp:hiddenfield id="hdnRefresh" runat="Server"></asp:hiddenfield>
                <asp:Table runat="server" ID="Table1">

                    <%-- Acceleration --%>
                    <asp:TableRow>
                        <asp:TableCell>
                            <asp:CheckBox ID="CheckBoxChartAcceleration" runat="server" AutoPostBack="true"
                                Text="Gyorsulás" TextAlign="Right" OnCheckedChanged="ChartCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="ChartAccelerationHolder" runat="server">
                                Grafikon típus:&nbsp;
                                <asp:DropDownList ID="DropDownAccelerationChartType" runat="server" AutoPostBack="true"
                                    onselectedindexchanged="dropdownChartType_SelectedIndexChanged" />
                                &nbsp;Szín:&nbsp;
                                <%--ColorPicker--%>
                                <asp:TextBox ID="TextBoxCPAcceleration" runat="server" Width="50" />
                                <asp:ImageButton runat="Server" ID="ImageButtonCPAcceleration" style="float:right;margin:0 3px" ImageUrl="~/images/cp_button.png" 
                                    AlternateText="Kattintson a színválasztó képre!" />
                                <ajaxToolkit:ColorPickerExtender ID="TextBoxCPAcceleration_ColorPickerExtender" PopupButtonID="ImageButtonCPAcceleration"
                                    runat="server" Enabled="True" TargetControlID="TextBoxCPAcceleration" SampleControlID="TextBoxCPAcceleration" OnClientColorSelectionChanged="colorChanged"/>
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>

                    <%-- Temperature --%>
                    <asp:TableRow>
                        <asp:TableCell>
                            <asp:CheckBox ID="CheckBoxChartTemperature" runat="server" AutoPostBack="true"
                                Text="Hőmérséklet" TextAlign="Right" OnCheckedChanged="ChartCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="ChartTemperatureHolder" runat="server">
                                Grafikon típus:&nbsp;
                                <asp:DropDownList ID="DropDownTemperatureChartType" runat="server" AutoPostBack="true"
                                    onselectedindexchanged="dropdownChartType_SelectedIndexChanged" />
                                &nbsp;Szín:&nbsp;
                                <%--ColorPicker--%>
                                <asp:TextBox ID="TextBoxCPTemperature" runat="server" Width="50" />
                                <asp:ImageButton runat="Server" ID="ImageButtonCPTemperature" style="float:right;margin:0 3px" ImageUrl="~/images/cp_button.png" 
                                    AlternateText="Kattintson a színválasztó képre!" />
                                <ajaxToolkit:ColorPickerExtender ID="TextBoxCPTemperature_ColorPickerExtender" PopupButtonID="ImageButtonCPTemperature"
                                    runat="server" Enabled="True" TargetControlID="TextBoxCPTemperature" SampleControlID="TextBoxCPTemperature" OnClientColorSelectionChanged="colorChanged" />
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>

                    <%-- Pulse --%>
                    <asp:TableRow>
                        <asp:TableCell>
                            <asp:CheckBox ID="CheckBoxChartPulse" runat="server" AutoPostBack="true"
                                Text="Pulzus" TextAlign="Right" OnCheckedChanged="ChartCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="ChartPulseHolder" runat="server">
                                Grafikon típus:&nbsp;
                                <asp:DropDownList ID="DropDownPulseChartType" runat="server" AutoPostBack="true"
                                    onselectedindexchanged="dropdownChartType_SelectedIndexChanged" />
                                &nbsp;Szín:&nbsp;
                                <%--ColorPicker--%>
                                <asp:TextBox ID="TextBoxCPPulse" runat="server" Width="50" />
                                <asp:ImageButton runat="Server" ID="ImageButtonCPPulse" style="float:right;margin:0 3px" ImageUrl="~/images/cp_button.png" 
                                    AlternateText="Kattintson a színválasztó képre!" />
                                <ajaxToolkit:ColorPickerExtender ID="TextBoxCPPulse_ColorPickerExtender" PopupButtonID="ImageButtonCPPulse"
                                    runat="server" Enabled="True" TargetControlID="TextBoxCPPulse" SampleControlID="TextBoxCPPulse" OnClientColorSelectionChanged="colorChanged" />
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>

                    <%-- BloodOxygen --%>
                    <asp:TableRow>
                        <asp:TableCell>
                            <asp:CheckBox ID="CheckBoxChartBloodOxygen" runat="server" AutoPostBack="true"
                                Text="Véroxigén" TextAlign="Right" OnCheckedChanged="ChartCheckBox_CheckedChanged" />
                        </asp:TableCell>
                        <asp:TableCell>
                            <asp:PlaceHolder ID="ChartBloodOxygenHolder" runat="server">
                                Grafikon típus:&nbsp;
                                <asp:DropDownList ID="DropDownBloodOxygenChartType" runat="server" AutoPostBack="true"
                                    onselectedindexchanged="dropdownChartType_SelectedIndexChanged" />
                                &nbsp;Szín:&nbsp;
                                <%--ColorPicker--%>
                                <asp:TextBox ID="TextBoxCPBloodOxygen" runat="server" Width="50" />
                                <asp:ImageButton runat="Server" ID="ImageButtonCPBloodOxygen" style="float:right;margin:0 3px" ImageUrl="~/images/cp_button.png" 
                                    AlternateText="Kattintson a színválasztó képre!" />
                                <ajaxToolkit:ColorPickerExtender ID="TextBoxCPBloodOxygen_ColorPickerExtender" PopupButtonID="ImageButtonCPBloodOxygen"
                                    runat="server" Enabled="True" TargetControlID="TextBoxCPBloodOxygen" SampleControlID="TextBoxCPBloodOxygen" OnClientColorSelectionChanged="colorChanged" />
                            </asp:PlaceHolder>
                        </asp:TableCell>
                    </asp:TableRow>
                    
                </asp:Table>
        </fieldset>
        
        <br />

        <asp:PlaceHolder ID="ChartHolder" runat="server">
            <p><b>A keresési feltételeknek megfelelő mérések grafikonon ábrázolva:</b></p>
            <asp:Chart ID="ChartMeasures" runat="server" DataSourceID="MeasuresDataSource" 
                BorderlineColor="LightGray" BorderlineDashStyle="Dash" Width="500" 
                Height="500" onload="ChartMeasures_Load" 
                BackColor="GradientActiveCaption" BackGradientStyle="Center">
                <chartareas>
                    <asp:ChartArea Name="ChartMeasuresArea" BackColor="#D2D7DD" BackGradientStyle="DiagonalLeft">
                        <Area3DStyle Enable3D="True" Inclination="20" LightStyle="Realistic" 
                            WallWidth="0" />
                            <AxisX Title="Mérés dátuma" />
                    </asp:ChartArea>
                </chartareas>
                <Legends>
                    <asp:Legend Name="AccelerationLegend" Title="Magyarázat" Enabled="True" Docking="Bottom" ShadowOffset="1" LegendStyle="Table" IsEquallySpacedItems="True" Alignment="Far" BorderColor="#666666" BackGradientStyle="TopBottom" BackColor="#B1CBE4" />
                </Legends>
                <Titles>
                    <asp:Title Text="A kiválasztott típusú mért értékek grafikonja" Alignment="TopCenter" />
                </Titles>
            </asp:Chart>
        </asp:PlaceHolder>
    </ContentTemplate>
    <Triggers>
        <asp:AsyncPostBackTrigger ControlID="ButtonFilter" EventName="Click" />
    </Triggers>
</asp:UpdatePanel>
