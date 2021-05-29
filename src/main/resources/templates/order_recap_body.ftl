<table style="font-size:14px;font-family:Arial,Helvetica,sans-serif;line-height:19px;color:#333;" bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0" width="830" align="center">
    <tbody>
    <tr>
        <td width="830">
            <table border="0" cellpadding="0" cellspacing="0" width="830" align="center">
                <tbody>
                <tr>
                    <td width="30">&nbsp;</td>
                    <td width="770">
                        <table border="0" cellpadding="0" cellspacing="0" width="770" align="center">
                            <tbody>
                            <#include "header.ftl">
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;padding-left: 2px;">
                                    <strong style="color: #c7b07c;">Kedves ${userFirstName} ${userLastName},</strong>
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;padding-left: 2px;">
                                    Köszönjük a FitForFun weboldalon leadott megrendelést. Megrendelése megérkezett, feldolgozása elkezdődött.
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;padding-left: 2px;">
                                    Ha kérdése van a rendeléssel kapcsolatban, a <a style="color: #c7b07c;" href="fitforfun@gmail.com">fitforfun@gmail.com</a> címre írjon nekünk.
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <table border="0" cellpadding="5" cellspacing="2" width="770" align="center">
                                        <thead>
                                        <tr>
                                            <th colspan="2" style="background-color: #cccccc; color: black;">A megrendelés részletei</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Rendelésszám</td>
                                            <td width="385" style="background-color: #eeeeee;">${trackingNumber}</td>
                                        </tr>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Rendelés dátuma</td>
                                            <td width="385" style="background-color: #eeeeee;">${date}</td>
                                        </tr>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Email</td>
                                            <td width="385" style="background-color: #eeeeee;"><a style="color: #c7b07c;" href="mailto:${email}">${email}</a></td>
                                        </tr>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Telefonszám</td>
                                            <td width="385" style="background-color: #eeeeee;">${phone}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <table border="0" cellpadding="5" cellspacing="2" width="770" align="center">
                                        <thead>
                                        <tr>
                                            <th colspan="2" style="background-color: #cccccc; color: black;">Számlázási cím</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Ország</td>
                                            <td width="385" style="background-color: #eeeeee;">${address.country}</td>
                                        </tr>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Város</td>
                                            <td width="385" style="background-color: #eeeeee;">${address.city.cityName}</td>
                                        </tr>
                                        <tr>
                                            <td width="385" style="background-color: #eeeeee;">Utca</td>
                                            <td width="385" style="background-color: #eeeeee;">${address.street}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <table border="0" cellpadding="5" cellspacing="2" width="770" align="center">
                                        <thead>
                                        <tr>
                                            <th style="background-color: #cccccc; color: black;">Megnevezés</th>
                                            <th style="background-color: #cccccc; color: black;">Egység ár</th>
                                            <th style="background-color: #cccccc; color: black;">Mennyiség</th>
                                            <th style="background-color: #cccccc; color: black;">Ár</th>
                                            <th style="background-color: #cccccc; color: black;">&nbsp;</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list items as oi>
                                            <tr>
                                                <td style="background-color: #eeeeee;">${oi.shopItem.name}</td>
                                                <td align="right" style="background-color: #eeeeee;">${oi.shopItem.price}</td>
                                                <td style="background-color: #eeeeee;">${oi.quantity}</td>
                                                <td align="right" style="background-color: #eeeeee;">${oi.price}</td>
                                                <td style="background-color: #eeeeee;">HUF</td>
                                            </tr>
                                        </#list>
                                        <tr>
                                             <td align="right" colspan="5" style="background-color: #eeeeee;">Összesen</td>
                                             <td colspan="3" align="right" style="background-color: #eeeeee;">${total}</td>
                                             <td style="background-color: #eeeeee;">HUF</td>
                                        </tr>
                                        <tr>
                                            <td align="right" colspan="5" style="background-color: #eeeeee;">Szállítási díj</td>
                                            <td colspan="3" align="right" style="background-color: #eeeeee;">0</td>
                                            <td style="background-color: #eeeeee;">HUF</td>
                                        </tr>
                                        <tr>
                                            <td align="right" colspan="5" style="background-color: #eeeeee; font-weight: bold;">Fizetendő</td>
                                            <td colspan="3" align="right" style="background-color: #eeeeee; font-weight: bold;">${total}</td>
                                            <td style="background-color: #eeeeee; font-weight: bold;">HUF</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <#include "footer.ftl">
                            </tbody>
                        </table>
                    </td>
                    <td width="30">&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>
