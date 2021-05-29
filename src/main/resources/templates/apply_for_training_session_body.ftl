<table style="font-size:14px;font-family:Arial,Helvetica,sans-serif;line-height:19px;color:#333;" bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0" width="630" align="center">
    <tbody>
    <tr>
        <td width="630">
            <table border="0" cellpadding="0" cellspacing="0" width="630" align="center">
                <tbody>
                <tr>
                    <td width="30">&nbsp;</td>
                    <td width="570">
                        <table border="0" cellpadding="0" cellspacing="0" width="570" align="center">
                            <tbody>
                            <#include "header.ftl">
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <strong style="color: #c7b07c;">Kedves ${firstName} ${lastName}!</strong>
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <p>Az Ön által tartott ${sessionDay}-i napon ${sessionStart}-${sessionEnd}-ig tartó ${sessionType}
                                     órájára  ${client.firstName} ${client.lastName} nevű felhasználónk jelentkezett.<p>
                                     <p> Elérhetőségei: email: ${clientContactData.email} mobil: ${clientContactData.telNumber}
                                </td>
                            </tr>
                            <tr>
                                <td height="15" style="font-size:1px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    Üdvözlettel:
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    <strong style="color: #c7b07c;">FitForFun vezetősége</strong>
                                </td>
                            </tr>
                            <#include "footer.ftl">
                            </tbody>
                        </table>l
                    </td>
                    <td width="30">&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>