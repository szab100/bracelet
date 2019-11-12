<%@ Control Language="C#" AutoEventWireup="true" CodeFile="UserProfile.ascx.cs" Inherits="UserProfile" %>
<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>
 
<fieldset style="width:50%">
    <legend>Felhasználói profil</legend>
<p></p>
<div ><b>Fénykép:</b></div>
<p></p>
<table cellpadding="2">
   <tr>
    <td width="130"></td>
    <td width="300">    
        <asp:Image ID="ImageProfile" runat="server" Width="100px" Height="100px" />
        <br /><br />
        Új kép feltöltése:
        <asp:FileUpload ID="FileUpload1" runat="server" />
        <asp:Button ID="UploadButton" runat="server" Text="Feltölt" onclick="UploadButton_Click" />
    </td>
   </tr>
</table>
<p></p>
<div ><b>Személyes adatok:</b></div>
<p></p>
<table cellpadding="2">
   <tr>
      <td width="130">Keresztnév:</td>
      <td width="300">
         <asp:TextBox ID="txtFirstName" runat="server" Width="99%"></asp:TextBox>
      </td>
   </tr>
   <tr>
      <td >Vezetéknév:</td>
      <td>
         <asp:TextBox ID="txtLastName" runat="server" Width="99%" />
      </td>
   </tr>
   <tr>
      <td >Nem:</td>
      <td>
         <asp:DropDownList runat="server" ID="ddlGenders">
            <asp:ListItem Text="Kérjük válasszon..." Value="" Selected="True" />
            <asp:ListItem Text="Férfi" Value="M" />
            <asp:ListItem Text="Nő" Value="F" />
         </asp:DropDownList>
      </td>
   </tr>
   <tr>
      <td>Születési dátum:</td>
      <td>
         <asp:TextBox ID="txtBirthDate" runat="server" Width="90%" />
         <asp:Image ID="imageCalendar" runat="server" ImageUrl="~/Images/calendar.jpg" />&nbsp;
         <asp:CalendarExtender ID="CalendarExtender1" runat="server" PopupPosition="Right" PopupButtonID="imageCalendar"
            TargetControlID="txtBirthDate" Format="yyyy.MM.dd"/>
        
         <asp:CompareValidator runat="server" ID="valBirthDateFormat"
            ControlToValidate="txtBirthDate"
            SetFocusOnError="true" Display="Dynamic" Operator="DataTypeCheck"
            Type="Date" ErrorMessage="A születési év formátuma érvénytelen."
            ValidationGroup="EditProfile">
            <br />A születési év formátuma érvénytelen.
         </asp:CompareValidator>
      </td>
   </tr>
   <tr>
      <td>Foglalkozás:</td>
      <td>         
         <asp:DropDownList ID="ddlOccupations" runat="server" Width="99%">
            <asp:ListItem Text="Válasszon egyet..." Value="" Selected="True" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Adminisztráció / Irodai munka vezető" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Adminisztrátor" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Dokumentumkezelő" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Recepciós" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Szakmai asszisztens" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Személyi asszisztens" />
            <asp:ListItem OptionGroup="Adminisztráció / Irodai munka" Text="Titkárnő / Titkár" />
            <asp:ListItem OptionGroup="Bank / Biztosítás / Tőzsde" Text="Bank / Biztosítás / Tőzsde vezető" />
            <asp:ListItem OptionGroup="Bank / Biztosítás / Tőzsde" Text="Banki / Biztosítási szakügyintéző" />
            <asp:ListItem Text="Befektetési tanácsadó" OptionGroup="Bank / Biztosítás / Tőzsde" />
            <asp:ListItem Text="Bróker" OptionGroup="Bank / Biztosítás / Tőzsde" />
            <asp:ListItem Text="Finanszírozás" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Hálózatépítés" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Kockázatkezelő" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Tanácsadás / Elemzés" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Termékfejlesztés" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Tervezés" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Treasury" OptionGroup="Bank / Biztosítás / Tőzsde"/>
            <asp:ListItem Text="Állatorvos / Állatgondozás" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Egészségügy / Szépség vezető" OptionGroup="Egészségügy / Szépség" />
            <asp:ListItem Text="Egészségügyi asszisztens" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Egészségügyi szakember" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Gyógyszerész" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Laboráns" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Nővér / Szakápoló" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Orvos / Szakorvos" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Orvoslátogató / Patikalátogató" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem Text="Szociális munkás" OptionGroup="Egészségügy / Szépség"/>
            <asp:ListItem OptionGroup="Egyéb" Text="Tanuló" />
            <asp:ListItem OptionGroup="Egyéb" Text="Nyugdíjas" />
            <asp:ListItem OptionGroup="Egyéb" Text="Munkanélküli" />
            <asp:ListItem Text="Építészmérnök" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Építőipar / Ingatlan vezető" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Építőmérnök" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Épületgépész" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Épületkarbantartó" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Értékbecslő" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Ingatlanproject munkatárs" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Kivitelező / General kivitelező" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Üzemeltető" OptionGroup="Építőipar / Ingatlan"/>
            <asp:ListItem Text="Értékesítés / Kereskedelem / Üzlet vezető" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Értékesítő / Területi képviselő" OptionGroup="Értékesítés / Kereskedelem / Üzlet" />
            <asp:ListItem Text="Hirdetésszervezés" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Informatikai értékesítő" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Ingatlanértékesítő" OptionGroup="Értékesítés / Kereskedelem / Üzlet" />
            <asp:ListItem Text="Kereskedő / Eladó" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Key Account Manager" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Közvetlen értékesítő" OptionGroup="Értékesítés / Kereskedelem / Üzlet" />
            <asp:ListItem Text="Külkereskedelmi bonyolító" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Mérnök - Értékesítő" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Online értékesítés" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Pénzügyi szolgáltatások értékesítése" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Piacelemző" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Telefonos értékesítő" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Termékfelelős / Termékmenedzser" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Üzletfejlesztő" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Üzleti elemző" OptionGroup="Értékesítés / Kereskedelem / Üzlet"/>
            <asp:ListItem Text="Anyagmozgatás / Rakodás" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="Betanított munka" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="Csomagolás / Feltöltés" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="Egyéb fizikai munka" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="Gépkezelő" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="Takarítás / Tisztítás" OptionGroup="Fizikai munka / Segédmunka"/>
            <asp:ListItem Text="CAD tervező / Műszaki rajzoló" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Egyéb mérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Elektromérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Gépészmérnök" OptionGroup="Gyártás / Termelés / Mérnök" />
            <asp:ListItem Text="Gyártás / Termelés / Mérnök vezető" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Gyártás technikus" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Hang- és világítástechnikai mérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Karbantartó" OptionGroup="Gyártás / Termelés / Mérnök" />
            <asp:ListItem Text="Könnyűipari mérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Közlekedési mérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Laboros" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Minőségbiztosítás / Ellenőrzés" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Munkavédelem / Biztonságtechnika" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Műszaki menedzser" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Műszakvezető" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Nyomdász" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Operátor" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Projektmenedzser" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Termékfejlesztés / Tervezés" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Termelésirányítás" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Üzemmérnök" OptionGroup="Gyártás / Termelés / Mérnök" />
            <asp:ListItem Text="Vegyész / Vegyészmérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Villamosmérnök" OptionGroup="Gyártás / Termelés / Mérnök"/>
            <asp:ListItem Text="Bérszámfejtés / TB ügyintézés / Munkaügy" OptionGroup="HR / Emberi erőforrás / Munkaügy" />
            <asp:ListItem Text="HR / Emberi erőforrás / Munkaügy vezető" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="HR generalista / Specialista" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="Munkaerő közvetítés / Kölcsönzés / Fejvadászat" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="Személyzeti tanácsadás / Szervezetfejlesztés" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="Tréning / Coaching" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="Vállalati HR" OptionGroup="HR / Emberi erőforrás / Munkaügy"/>
            <asp:ListItem Text="Adatbázisszakértő" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Hálózati és rendszermérnök" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="IT / Telecom vezető" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="IT support / Helpdesk" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="IT Tanácsadó / Elemző / Auditor" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Programozó / Fejlesztő" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Projektmanager" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Rendszergazda" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Rendszerintegrátor" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Rendszerüzemeltető / Karbantartó" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Telekommunikáció" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Tesztelő / Tesztmérnök" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Vállalatirányítási rendszer / SAP" OptionGroup="IT / Telekommunikáció"/>
            <asp:ListItem Text="Bankjog / Pénzügyi szektor" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Jog / Közigazgatás vezető" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Közigazgatás / Tisztviselő / Közalkalmazott" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Nonprofit szervezetek" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Pályázatírás" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Ügyvéd / Jogi tanácsadó / Jogtanácsos" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Ügyvédjelölt / Gyakornok / Jogi asszisztens" OptionGroup="Jog / Közigazgatás"/>
            <asp:ListItem Text="Agrármérnök" OptionGroup="Környezet / Mezőgazdaság"/>
            <asp:ListItem Text="Biotechnika" OptionGroup="Környezet / Mezőgazdaság"/>
            <asp:ListItem Text="Hulladékgazdálkodás" OptionGroup="Környezet / Mezőgazdaság"/>
            <asp:ListItem Text="Környezet / Mezőgazdaság vezető" OptionGroup="Környezet / Mezőgazdaság"/>
            <asp:ListItem Text="Környezetvédelem / Vízgazdálkodás" OptionGroup="Környezet / Mezőgazdaság" />
            <asp:ListItem Text="Mezőgazdaság / Élelmiszeripar" OptionGroup="Környezet / Mezőgazdaság"/>
            <asp:ListItem Text="Divat-stílustervező" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Grafikus / Képszerkesztő / DTP operátor" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Kirakatrendező" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Márka- és termékmenedzser" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Marketing" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Marketing / Média / Művészet vezető" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Médiatervező / Tanácsadó" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Online marketing" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Piackutató" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="PR" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Szerkesztő / Szövegíró" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Webdesigner" OptionGroup="Marketing / Média / Művészet"/>
            <asp:ListItem Text="Bébiszitter" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Egyéb tudomány" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Fizikus" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Kutató" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Matematikus" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Nyelvtanár" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Oktatás / Tudomány vezető" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Oktatásszervező" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Oktató" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Óvónő / Dajka" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Tanító / Tanár / Pedagógus" OptionGroup="Oktatás / Tudomány"/>
            <asp:ListItem Text="Egyéb képzések munkalehetőséggel" OptionGroup="Pályakezdő / Gyakornoki programok"/>
            <asp:ListItem Text="Management trainee programok" OptionGroup="Pályakezdő / Gyakornoki programok"/>
            <asp:ListItem Text="Pályakezdő / gyakornoki programok" OptionGroup="Pályakezdő / Gyakornoki programok"/>
            <asp:ListItem Text="Szakmai gyakorlat" OptionGroup="Pályakezdő / Gyakornoki programok"/>
            <asp:ListItem Text="Adótanácsadó / Szakértő" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Cash management" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Cégvezető / Igazgató" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Corporate Treasurer" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Elemző / Tanácsadó" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Kintlévőség kezelő / Behajtó" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Kontrolling" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Könyvelés" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Könyvvizsgáló" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Pénzügyi / Számviteli vezető" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Számlázó / Pénztáros" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Ügyvezető" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Vám" OptionGroup="Pénzügy / Számvitel / Menedzsment"/>
            <asp:ListItem Text="Ács / Asztalos" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Autószerelő" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Bolti eladó" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Cukrász" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Egyéb szakmunka" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Esztergályos / Marós" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Gépkezelő" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Gépszerelő" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Hegesztő" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Kertész" OptionGroup="Szakmunka" />
            <asp:ListItem Text="Lakatos / Géplakatos" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Műszerész" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Pincér" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Szabó / Varró" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Szakács" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Villanyszerelő" OptionGroup="Szakmunka"/>
            <asp:ListItem Text="Anyaggazdálkodó" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Beszerző" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Flottakezelő" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Logisztikus / Fuvarszervező" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Raktározás Készletezés" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Szállítás / Logisztika vezető" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Szállítmányozó" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Személyszállító / Gépkocsivezető" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Teherszállító / Sofőr / Futár" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Vámügyintéző" OptionGroup="Szállítás / Logisztika"/>
            <asp:ListItem Text="Közterület felügyelő / Parkolóőr" OptionGroup="Személy- és vagyonvédelem"/>
            <asp:ListItem Text="Portás" OptionGroup="Személy- és vagyonvédelem"/>
            <asp:ListItem Text="Személy- és vagyonőr / Biztonsági őr" OptionGroup="Személy- és vagyonvédelem"/>
            <asp:ListItem Text="Tűzvédelmi / Biztonsági szakértő" OptionGroup="Személy- és vagyonvédelem"/>
            <asp:ListItem Text="Call center munkatárs" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Diszpécser" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Kisvállalati ügyfélkapcsolat" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Lakossági ügyfélkapcsolat" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="SSC (Shared Service Center)" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Telefonos operátor" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Tolmács / Fordító" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Ügyfélszolgálat" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Ügyfélszolgálat / Ügyfélkapcsolat vezető" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Ügyfélszolgálati képviselő" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="VIP ügyfélkapcsolat" OptionGroup="Ügyfélszolgálat / Ügyfélkapcsolat"/>
            <asp:ListItem Text="Éttermi vendéglátás" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="HACCP szakértő" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Közétkeztetés / Üzemeltetés" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Légi utaskísérő" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Rendezvények" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Szállodaipar" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Turizmus / Utazásszervezés" OptionGroup="Vendéglátás / Idegenforgalom"/>
            <asp:ListItem Text="Vendéglátás / Idegenforgalom vezető" OptionGroup="Vendéglátás / Idegenforgalom"/>

         </asp:DropDownList>
      </td>
   </tr>
   <tr>
      <td>Weboldal:</td>
      <td>
         <asp:TextBox ID="txtWebsite" runat="server" Width="99%" />
      </td>
   </tr>
</table>
<p></p>
<div ><b>Cím:</b></div>
<p></p>
<table cellpadding="2">
   <tr>
      <td width="130" class="fieldname">Utca:</td>
      <td width="300">
         <asp:TextBox runat="server" ID="txtStreet" Width="99%" />
      </td>
   </tr>
   <tr>
      <td>Város:</td>
      <td><asp:TextBox runat="server" ID="txtCity" Width="99%" /></td>
   </tr>
   <tr>
      <td>Irányítószám:</td>
      <td><asp:TextBox runat="server" ID="txtPostalCode" Width="99%" /></td>
   </tr>
   <tr>
      <td>Megye:</td>
      <td><asp:TextBox runat="server" ID="txtCounty" Width="99%" /></td>
   </tr>
</table>
<p></p>
<div ><b>További elérhetőségek:</b></div>
<p></p>
<table cellpadding="2">
   <tr>
      <td width="130">Telefon:</td>
      <td width="300"><asp:TextBox runat="server" ID="txtPhone" Width="99%" /></td>
   </tr>
   <tr>
      <td>Mobiltelefon:</td>
      <td><asp:TextBox runat="server" ID="txtMobile" Width="99%" /></td>
   </tr>
   <tr>
      <td>Fax:</td>
      <td><asp:TextBox runat="server" ID="txtFax" Width="99%" /></td>
   </tr>
</table>
<table cellpadding="2" width="97%">
   <tr>
       <td align="right">
          <asp:Label ID="lblProfileOK" Text="A profil frissítésre került!" Visible="false" runat="server"/>
          <asp:Button ID="btnUpdateProfile" Text="Adatok mentése" runat="server"
           ValidationGroup="EditProfile" OnClick="btnUpdateProfile_Click"/>
       </td>
   </tr>
</table>
</fieldset>