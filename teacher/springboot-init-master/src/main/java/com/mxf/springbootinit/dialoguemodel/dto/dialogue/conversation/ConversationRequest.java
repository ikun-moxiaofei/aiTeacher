package com.mxf.springbootinit.dialoguemodel.dto.dialogue.conversation;

import com.mxf.springbootinit.dialoguemodel.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationRequest {

    private String userId;

    private String conversationId;

    private Message temp_message;

}