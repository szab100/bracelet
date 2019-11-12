<%@ Control Language="C#" AutoEventWireup="true" CodeFile="UserTolerances.ascx.cs" Inherits="UserTolerances" %>

<asp:UpdatePanel ID="TolerancePanel" runat="server">
    <ContentTemplate>

        <fieldset style="width:50%">
            <legend>Toleranciamaszk beállítás</legend>
            <p></p>
            
            <asp:Table runat="server">
                <asp:TableRow>
                    <asp:TableCell>
                        Típus:&nbsp;
                        <asp:DropDownList ID="DropDownToleranceType" runat="server" AutoPostBack="true" 
                            OnSelectedIndexChanged="DropDownToleranceType_SelectedIndexChanged">
                            <asp:ListItem Text="- Válasszon -" Value="" />
                            <asp:ListItem Text="Gyorsulás" Value="acc" />
                            <asp:ListItem Text="Pulzus" Value="pulse" />
                            <asp:ListItem Text="Testhőmérséklet" Value="temp" />
                            <asp:ListItem Text="Véroxigén" Value="blox" />
                        </asp:DropDownList>
                    </asp:TableCell>
                    <asp:TableCell>
                        <asp:PlaceHolder ID="ToleranceValuesHolder" runat="server">
                            <asp:PlaceHolder ID="LowValueLabelHolder" runat="server">
                                Alsó határ:&nbsp;
                            </asp:PlaceHolder>
                            <asp:TextBox ID="TextBoxLow" runat="server" Width="40" />
                            <asp:CompareValidator ControlToValidate="TextBoxLow" runat="server" Type="Double" Text="*" Operator="DataTypeCheck" ValidationGroup="AllValidators"
                                ErrorMessage="A megadott alsó határ nem megfelelő." Display="Dynamic" ID="ValidatorLow" />
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            Felső határ:&nbsp;
                            <asp:TextBox ID="TextBoxHigh" runat="server" Width="40" />
                            <asp:CompareValidator ControlToValidate="TextBoxHigh" runat="server" Type="Double" Text="*" Operator="DataTypeCheck" ValidationGroup="AllValidators"
                                ErrorMessage="A megadott felső határ nem megfelelő." Display="Dynamic" />
                            &nbsp;
                        </asp:PlaceHolder>
                        <br />
                    </asp:TableCell>
                </asp:TableRow>
            </asp:Table>
            <asp:Button ID="ButtonSubmit" Text="Mentés" runat="server" ValidationGroup="AllValidators" OnClick="ButtonSubmit_Click" Style="float:right" />
            <asp:validationsummary id="ErrorSummary" runat="server" ValidationGroup="AllValidators" ForeColor="Red" />
            <asp:Label ID="MessageLabel" runat="server" />
        </fieldset>
    </ContentTemplate>
    <Triggers>
    </Triggers>
</asp:UpdatePanel>