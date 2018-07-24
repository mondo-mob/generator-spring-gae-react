<#include "./standard-email.ftl" />

<@standardEmail title="User invitation">
    <p>Hi there,</p>
    <p>You've been invited to collaborate on FIXME app. Click the button below to complete your account setup.</p>

    <table border="0" cellpadding="0" cellspacing="0" class="btn btn-primary">
        <tbody>
            <tr>
                <td align="left">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tbody>
                            <tr>
                                <td><a href="${invite_link}" target="_blank">Complete setup</a></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>

    <p>Thanks!</p>
</@standardEmail>
