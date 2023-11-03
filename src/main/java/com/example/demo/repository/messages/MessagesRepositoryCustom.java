package com.example.demo.repository.messages;

import java.util.*;
import org.springframework.data.domain.Pageable;
import com.example.demo.model.dto.messages.GetMessages;

// list 페이징 처리를 위해 custom repository 생성
public interface MessagesRepositoryCustom {

    List<GetMessages> getList(Pageable pageReqeust);
    
}
