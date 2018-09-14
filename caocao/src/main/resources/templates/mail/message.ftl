<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息通知</title>
</head>

<style type="text/css">
    .table_1 {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        width: 100%;
        border-collapse: collapse;
    }

    .td_1, .th_1 {
        font-size: 1em;
        border: 1px solid #5B4A42;
        padding: 3px 7px 2px 7px;
    }

    .th_1 {
        font-size: 1.1em;
        text-align: center;
        padding-top: 5px;
        padding-bottom: 4px;
        background-color: #24A9E1;
        color: #ffffff;
    }
</style>
<body>
<div>
    <h2>邮件消息通知</h2>
    <table class="table_1" id="customers">
        <tr>
            <th class="th_1">MessageCode</th>
            <th class="th_1">MessageStatus</th>
            <th class="th_1">Cause</th>
        </tr>
        <tr>
            <td class="td_1">${(params.messageCode)!""}</td>
            <td class="td_1">${(params.messageStatus)!""}</td>
            <td class="td_1">${(params.cause)!""}</td>
        </tr>
    </table>
</div>
</body>
</html>