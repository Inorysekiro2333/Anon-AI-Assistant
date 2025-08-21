package com.yupi.yuaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.yupi.yuaicodemother.model.dto.chathistory.ChatHistoryQueryRequest;
import com.yupi.yuaicodemother.model.entity.ChatHistory;
import com.yupi.yuaicodemother.model.entity.User;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author by Inory
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 添加聊天消息的方法
     *
     * @param appId       应用ID，用于标识不同的应用
     * @param message     聊天消息的具体内容
     * @param messageType 消息的类型，如文本、图片等
     * @param userId      发送消息的用户ID
     * @return 返回一个布尔值，表示消息是否成功添加
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 根据应用ID删除相关数据
     *
     * @param appId 应用的唯一标识符ID
     * @return 删除操作是否成功执行，返回true表示成功，false表示失败
     */
    boolean deleteByAppId(Long appId);

    /**
     * 根据聊天历史查询请求参数获取查询构造器
     *
     * @param chatHistoryQueryRequest 聊天历史查询请求对象，包含查询条件
     * @return QueryWrapper 返回一个用于构建数据库查询条件的QueryWrapper对象
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    /**
     * 根据应用ID分页查询聊天记录
     *
     * @param appId          应用ID，用于筛选特定应用的聊天记录
     * @param pageSize       每页大小，控制返回记录的数量
     * @param lastCreateTime 上一次查询的创建时间，用于分页查询，获取该时间点之前的记录
     * @param loginUser      登录用户信息，用于权限验证
     * @return 返回一个包含聊天记录的Page对象，分页信息包含在内
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 将聊天历史记录加载到内存中
     *
     * @param appId      应用ID，用于标识特定的聊天会话
     * @param chatMemory 聊天记忆对象，用于存储加载的消息
     * @param maxCount   最大加载的历史消息数量
     * @return 实际加载的消息数量，如果加载失败则返回0
     */
    int loadChatHistoryToMemory(Long appId, MessageWindowChatMemory chatMemory, int maxCount);
}
