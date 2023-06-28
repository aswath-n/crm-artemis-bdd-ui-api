package com.maersk.crm.utilities;

import com.maersk.crm.api_step_definitions.APIConsumerRestController;
import com.maersk.crm.api_step_definitions.APIContactRecordController;
import com.maersk.crm.utilities.ApiTestDataUtil;
import com.maersk.crm.beans.ContactRecord;
import com.maersk.crm.beans.CreateAContactBean;
import com.maersk.dms.beans.outboundCorrespondence.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private static final ThreadLocal<World> world = ThreadLocal.withInitial(World::new);
    public final ThreadLocal<APIConsumerRestController> apiConsumerRestController = ThreadLocal.withInitial(APIConsumerRestController::new);
    public final ThreadLocal<APIContactRecordController> apiContactRecordController = ThreadLocal.withInitial(APIContactRecordController::new);
    public final ThreadLocal<APIClassUtil> apiClassUtil = ThreadLocal.withInitial(() -> APIClassUtil.getApiClassUtilThreadLocal(true));
    public final ThreadLocal<ApiTestDataUtil> apiTestDataUtil = ThreadLocal.withInitial(() -> ApiTestDataUtil.getApiTestDataUtilThreadLocal(true));
    public final ThreadLocal<CreateAContactBean> contactBean = ThreadLocal.withInitial(CreateAContactBean::new);
    public final ThreadLocal<ContactRecord> contactRecordBean = ThreadLocal.withInitial(ContactRecord::new);
    public final ThreadLocal<OutboundCorrespondenceBean> outboundCorrespondenceBean = ThreadLocal.withInitial(OutboundCorrespondenceBean::new);
    public final ThreadLocal<OutboundCorrespondenceDefinition> outboundCorrespondenceDefinition = ThreadLocal.withInitial(OutboundCorrespondenceDefinition::new);
    public final ThreadLocal<InboundCorrespondenceDefinition> inboundCorrespondenceDefinition = ThreadLocal.withInitial(InboundCorrespondenceDefinition::new);
    public final ThreadLocal<Map<String, Object>> inboundCorrespondenceTaskRule = ThreadLocal.withInitial(HashMap::new);
    public final ThreadLocal<OutboundCorrespondenceDefinitionChannel> outboundCorrespondenceDefinitionChannel = ThreadLocal.withInitial(OutboundCorrespondenceDefinitionChannel::new);
    public final ThreadLocal<OutboundCorrespondenceSettings> outboundCorrespondenceSettings = ThreadLocal.withInitial(OutboundCorrespondenceSettings::new);
    public final ThreadLocal<Response> response = new ThreadLocal<>();
    public final ThreadLocal<JsonPath> jsonPath = new ThreadLocal<>();
    public static final ThreadLocal<String> endpoint = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<String> error = ThreadLocal.withInitial(String::new);
    public static final ThreadLocal<Map<String, String>> save = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<Map<String, Object>> generalSave = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<Map<String, Object>> random = ThreadLocal.withInitial(HashMap::new);
    public static final ThreadLocal<List<String>> generalList = ThreadLocal.withInitial(ArrayList::new);
    public static ThreadLocal<Boolean> configSettingsPresent = new ThreadLocal<>();


    private World() {
    }

    public static World getWorld() {
        if (world.get() == null) {
            synchronized (world) {
                world.set(new World());
            }
        }
        return world.get();
    }

    public static World createNewWorld() {
        synchronized (world) {
            world.set(null);
            world.set(new World());
        }
        return world.get();
    }
}

