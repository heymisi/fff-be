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
                                    Ön Sikeresen regisztrált FitForFun weboldalunkon.  Ahhoz, hogy be tudjon jelentkezni utolsó lépésként meg kell erősítenie regisztrácíóját, amelyet az alábbi linkre kattintva tehet meg:
                                    <a style="color: #c7b07c;" href="https://fitforfun.herokuapp.com/registration/activate?token=${token}" target="_blank">https://fitforfun.herokuapp.com</a> !
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#333;">
                                    Üdvözöljük FitforFun regisztrált tagjaink körében!
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
