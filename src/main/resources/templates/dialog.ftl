<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            margin: 0;
            height: 100vh;
            background: #e6f0ff;
            display: flex;
            flex-direction: column;
        }

        .chat-header {
            padding: 14px 18px;
            background: #ffffff;
            border-bottom: 1px solid #dbe7ff;
            font-weight: 600;
            color: #1e3a8a;
        }

        .chat-container {
            flex: 1;
            overflow-y: auto;
            padding: 16px;
            display: flex;
            flex-direction: column;
            gap: 14px;
        }

        .empty {
            text-align: center;
            color: #64748b;
            margin-top: 40px;
        }

        /* Message row */
        .message {
            display: flex;
            align-items: flex-end;
            gap: 10px;
            max-width: 70%;
        }

        .message.left {
            align-self: flex-start;
        }

        .message.right {
            align-self: flex-end;
            flex-direction: row-reverse;
        }

        /* Avatar */
        .avatar {
            width: 38px;
            height: 38px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #dbe7ff;
        }

        /* Bubble */
        .bubble {
            padding: 10px 14px;
            border-radius: 16px;
            position: relative;
            box-shadow: 0 6px 14px rgba(37, 99, 235, 0.15);
        }

        .left .bubble {
            background: #ffffff;
            color: #1e293b;
            border-bottom-left-radius: 4px;
        }

        .right .bubble {
            background: linear-gradient(135deg, #2563eb, #3b82f6);
            color: #ffffff;
            border-bottom-right-radius: 4px;
        }

        .text {
            font-size: 14px;
            line-height: 1.4;
        }

        .date {
            font-size: 11px;
            opacity: 0.7;
            margin-top: 4px;
            text-align: right;
        }

                .chat-form {
                    display: flex;
                    gap: 8px;
                    padding: 10px 16px;
                    background: #ffffff;
                    border-top: 1px solid #dbe7ff;
                }

                .chat-input {
                    flex: 1;
                    padding: 8px 12px;
                    border-radius: 20px;
                    border: 1px solid #cbd5e1;
                    font-size: 14px;
                }

                .chat-send {
                    padding: 8px 16px;
                    border: none;
                    border-radius: 20px;
                    background: #2563eb;
                    color: white;
                    font-weight: 600;
                    cursor: pointer;
                    transition: transform 0.1s;
                }

                .chat-send:hover {
                    transform: translateY(-1px);
                    box-shadow: 0 6px 12px rgba(37, 99, 235, 0.25);
                }
    </style>
</head>
<body>

<div class="chat-header">
    Chat
</div>

<div class="chat-container">

<#if isEmpty>
    <div class="empty">No messages yet</div>
<#else>
    <#list messages as msg>

        <#assign isMine = (msg.user.id == currentUserId)>

        <div class="message ${isMine?string("right","left")}">
            <img src="${msg.user.avatar_url}" class="avatar" alt="avatar"/>

            <div class="bubble">
                <div class="text">
                    ${msg.text}
                </div>
                <div class="date">
                    ${msg.date}
                </div>
            </div>
        </div>

    </#list>
</#if>

</div>

<form method="post" class="chat-form">
    <input type="text" name="text" class="chat-input" placeholder="Type a message..." required />
    <button type="submit" class="chat-send">Send</button>
</form>

</body>
</html>
