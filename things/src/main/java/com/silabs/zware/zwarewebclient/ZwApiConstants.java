package com.silabs.zware.zwarewebclient;


import java.util.HashMap;
import java.util.List;

public class ZwApiConstants {
    private static ZwApiConstants single_zw_api_const_instance = null;

    public static ZwApiConstants getInstance(){
        if(single_zw_api_const_instance == null) {
            single_zw_api_const_instance = new ZwApiConstants();
        }
        return single_zw_api_const_instance;
    }
    private ZwApiConstants(){
        zwDevCategories = new HashMap<String,String>();
        zwDevCategoryDescriptions = new HashMap<String,String>();
        zwNetOps = new HashMap<String,String>();
        zwNetOpsDescriptions = new HashMap<String,String>();
        zwGenericDevClassDescriptions = new HashMap<String,String>();
        zwSpecificDevClassDescriptions = new HashMap<String,String>();
        zwInterfaceCommands = new HashMap<String,String>();
        zwErrorCodes = new HashMap<String,String>();
        zwErrorDescriptions = new HashMap<String,String>();

        /** @name Device_categories
         * Device Categories definition
         @{
         */
        zwDevCategories.put("DEV_CATEGORY_UNKNOWN","0");        /**< Unknown or unassigned category */
        zwDevCategories.put("DEV_SENSOR_ALARM","1");            /**< Sensor alarm */
        zwDevCategories.put("DEV_ON_OFF_SWITCH","2");           /**< On/off switch */
        zwDevCategories.put("DEV_POWER_STRIP","3");             /**< Power strip */
        zwDevCategories.put("DEV_SIREN","4");                   /**< Siren */
        zwDevCategories.put("DEV_VALVE","5");                   /**< Valve */
        zwDevCategories.put("DEV_SIMPLE_DISPLAY","6");          /**< Simple display */
        zwDevCategories.put("DEV_DOORLOCK_KEYPAD","7");         /**< Door lock with keypad */
        zwDevCategories.put("DEV_SUB_ENERGY_METER","8");        /**< Sub energy meter */
        zwDevCategories.put("DEV_ADV_WHL_HOME_ENER_METER","9"); /**< Advanced whole home energy meter */
        zwDevCategories.put("DEV_SIM_WHL_HOME_ENER_METER","10");/**< Simple whole home energy meter */
        zwDevCategories.put("DEV_SENSOR","11");	                /**< Sensor */
        zwDevCategories.put("DEV_LIGHT_DIMMER","12");	        /**< Light dimmer switch */
        zwDevCategories.put("DEV_WIN_COVERING_NO_POS","13");	/**< Window covering no position/endpoint */
        zwDevCategories.put("DEV_WIN_COVERING_EP","14");	    /**< Window covering end point aware */
        zwDevCategories.put("DEV_WIN_COVERING_POS_EP","15");	/**< Window covering position/end point aware */
        zwDevCategories.put("DEV_FAN_SWITCH","16");	            /**< Fan switch */
        zwDevCategories.put("DEV_RMT_CTL_MULTIPURPOSE","17");	/**< Remote control - multipurpose */
        zwDevCategories.put("DEV_RMT_CTL_AV","18");	            /**< Remote control - AV */
        zwDevCategories.put("DEV_RMT_CTL_SIMPLE","19");	        /**< Remote control - simple */
        zwDevCategories.put("DEV_UNRECOG_GATEWAY","20");	    /**< Gateway (unrecognized by client) */
        zwDevCategories.put("DEV_CENTRAL_CTLR","21");	        /**< Central controller */
        zwDevCategories.put("DEV_SET_TOP_BOX","22");	        /**< Set top box */
        zwDevCategories.put("DEV_TV","23");	                    /**< TV */
        zwDevCategories.put("DEV_SUB_SYS_CTLR","24");	        /**< Sub system controller */
        zwDevCategories.put("DEV_GATEWAY ","25");	            /**< Gateway */
        zwDevCategories.put("DEV_THERMOSTAT_HVAC","26");	    /**< Thermostat - HVAC */
        zwDevCategories.put("DEV_THERMOSTAT_SETBACK","27");	    /**< Thermostat - setback */
        zwDevCategories.put("DEV_WALL_CTLR","28");	            /**< Wall controller */

        zwDevCategoryDescriptions.put("0",  "Unknown");
        zwDevCategoryDescriptions.put("1",  "Sensor alarm");
        zwDevCategoryDescriptions.put("2",  "On/off switch");
        zwDevCategoryDescriptions.put("3",  "Power strip");
        zwDevCategoryDescriptions.put("4",  "Siren");
        zwDevCategoryDescriptions.put("5",  "Valve");
        zwDevCategoryDescriptions.put("6",  "Simple display");
        zwDevCategoryDescriptions.put("7",  "Door lock with keypad");
        zwDevCategoryDescriptions.put("8",  "Sub energy meter");
        zwDevCategoryDescriptions.put("9",  "Advanced whole home energy meter");
        zwDevCategoryDescriptions.put("10", "Simple whole home energy meter");
        zwDevCategoryDescriptions.put("11", "Sensor");
        zwDevCategoryDescriptions.put("12", "Light dimmer switch");
        zwDevCategoryDescriptions.put("13", "Window covering no position/endpoint ");
        zwDevCategoryDescriptions.put("14", "Window covering end point aware ");
        zwDevCategoryDescriptions.put("15", "Window covering position/end point awareµ");
        zwDevCategoryDescriptions.put("16", "Fan switch ");
        zwDevCategoryDescriptions.put("17", "Remote control - multipurpose");
        zwDevCategoryDescriptions.put("18", "Remote control - AV ");
        zwDevCategoryDescriptions.put("19", "Remote control - simple ");
        zwDevCategoryDescriptions.put("20", "Gateway (unrecognized by client) ");
        zwDevCategoryDescriptions.put("21", "Central controller ");
        zwDevCategoryDescriptions.put("22", "Set top box ");
        zwDevCategoryDescriptions.put("23", "TV ");
        zwDevCategoryDescriptions.put("24", "Sub system controller ");
        zwDevCategoryDescriptions.put("25", "Gateway");
        zwDevCategoryDescriptions.put("26", "Thermostat - HVAC ");
        zwDevCategoryDescriptions.put("27", "Thermostat - setback");
        zwDevCategoryDescriptions.put("28", "Wall controller");

        /** @name Network_operation
        * Network operation definition
        @{
        */
        zwNetOps.put("ZWNET_OP_NONE","0");                       /**< No operation is executing*/
        zwNetOps.put("ZWNET_OP_INITIALIZE","1");                 /**< Initialization operation*/
        zwNetOps.put("ZWNET_OP_ADD_NODE","2");                   /**< Add node operation*/
        zwNetOps.put("ZWNET_OP_RM_NODE","3");                    /**< Remove node operation*/
        zwNetOps.put("ZWNET_OP_RP_NODE","4");                    /**< Replace failed node operation*/
        zwNetOps.put("ZWNET_OP_RM_FAILED_ID","5");               /**< Remove failed node id operation*/
        zwNetOps.put("ZWNET_OP_INITIATE","6");                   /**< Initiation operation by controller*/
        zwNetOps.put("ZWNET_OP_UPDATE","7");                     /**< Update network topology from the SUC/SIS*/
        zwNetOps.put("ZWNET_OP_RESET","8");                      /**< Restore to factory default setting*/
        zwNetOps.put("ZWNET_OP_MIGRATE_SUC","9");                /**< Create primary controller by a SUC*/
        zwNetOps.put("ZWNET_OP_MIGRATE","10");                   /**< Migrate primary controller operation*/
        zwNetOps.put("ZWNET_OP_ASSIGN","11");                    /**< assign or deassign SUC/SIS operation*/
        zwNetOps.put("ZWNET_OP_LOAD_NW_INFO","12");              /**< Load detailed network and node info operation*/
        zwNetOps.put("ZWNET_OP_NODE_UPDATE","13");               /**< Update node info*/
        zwNetOps.put("ZWNET_OP_SEND_NIF","14");                  /**< Send node info frame*/
        zwNetOps.put("ZWNET_OP_NW_CHANGED","15");                /**< Network change detection*/
        zwNetOps.put("ZWNET_OP_NODE_CACHE_UPT","16");            /**< Update node cache info. (For internal use only)*/
        zwNetOps.put("ZWNET_OP_SAVE_NW","17");                   /**< Save network and node information to persistent storage. (For internal use only)*/
        zwNetOps.put("ZWNET_OP_SLEEP_NODE_UPT","18");            /**< Update sleeping detailed node information when it is awake. (For internal use only)*/
        zwNetOps.put("ZWNET_OP_FW_UPDT","19");                   /**< Firmware update*/
        zwNetOps.put("ZWNET_OP_HEALTH_CHK","20");                /**< Network health check*/

        zwNetOpsDescriptions.put("0",       "No operation is executin");
        zwNetOpsDescriptions.put("1",       "Initialization operation");
        zwNetOpsDescriptions.put("2",       "Add node operation");
        zwNetOpsDescriptions.put("3",       "Remove node operation");
        zwNetOpsDescriptions.put("4",       "Replace failed node operation");
        zwNetOpsDescriptions.put("5",       "Remove failed node id operation");
        zwNetOpsDescriptions.put("6",       "Initiation operation by controller");
        zwNetOpsDescriptions.put("7",       "Update network topology from the SUC/SIS");
        zwNetOpsDescriptions.put("8",       "Restore to factory default setting");
        zwNetOpsDescriptions.put("9",       "Create primary controller by a SUC");
        zwNetOpsDescriptions.put("10",      "Migrate primary controller operation");
        zwNetOpsDescriptions.put("11",      "Assign or deassign SUC/SIS operation");
        zwNetOpsDescriptions.put("12",      "Load detailed network and node info operation");
        zwNetOpsDescriptions.put("13",      "Update node info");
        zwNetOpsDescriptions.put("14",      "Send node info frame");
        zwNetOpsDescriptions.put("15",      "Network change detection");
        zwNetOpsDescriptions.put("16",      "Update node cache info. (For internal use only");
        zwNetOpsDescriptions.put("17",      "Save network and node information to persistent storage. (For internal use only");
        zwNetOpsDescriptions.put("18",      "Update sleeping detailed node information when it is awake. (For internal use only");
        zwNetOpsDescriptions.put("19",      "Firmware update");
        zwNetOpsDescriptions.put("20",      "Network health check");

        zwGenericDevClassDescriptions.put ("02","Static Controller");
        zwSpecificDevClassDescriptions.put("00","Specific Device Class Not Used");
        zwSpecificDevClassDescriptions.put("01","Central Controller Device Type");
        zwSpecificDevClassDescriptions.put("02","Scene Controller");
        zwSpecificDevClassDescriptions.put("03","Set Top Box Device Type");
        zwSpecificDevClassDescriptions.put("04","Sub System Controller Device Type");
        zwSpecificDevClassDescriptions.put("05","TV Device Type");
        zwSpecificDevClassDescriptions.put("06","Gateway Device Type");
        zwSpecificDevClassDescriptions.put("07","Binary Switch");

        zwGenericDevClassDescriptions.put ("16","On/Off Power Switch");
        zwSpecificDevClassDescriptions.put("00","NA");
        zwSpecificDevClassDescriptions.put("01","Binary Scene Switch");
        zwSpecificDevClassDescriptions.put("03","Power Strip");
        zwSpecificDevClassDescriptions.put("04","Siren");
        zwSpecificDevClassDescriptions.put("05","Valve");
        zwSpecificDevClassDescriptions.put("06","Color Tunable Binary");
        zwSpecificDevClassDescriptions.put("02","Irrigation Controller");

        zwInterfaceCommands.put("CMD_BASIC_SETUP", "1");
        zwInterfaceCommands.put("CMD_BASIC_GET",   "2");
        zwInterfaceCommands.put("CMD_BASIC_REPORT","3");
        zwInterfaceCommands.put("CMD_BASIC_SET",   "4");
        zwInterfaceCommands.put("CMD_BSWITCH_SETUP", "1");
        zwInterfaceCommands.put("CMD_BSWITCH_GET",   "2");
        zwInterfaceCommands.put("CMD_BSWITCH_REPORT","3");
        zwInterfaceCommands.put("CMD_BSWITCH_SET",   "4");

        zwErrorCodes.put("ZW_NO_ERROR","0");
        zwErrorCodes.put("ZW_DEFAULT_ERROR","1");

        zwErrorDescriptions.put("0","Success/No Error condition detected");
        zwErrorDescriptions.put("1","Z-Wave default error");

    }

    public HashMap<String,String> zwDevCategories;//device categories
    public HashMap<String,String> zwDevCategoryDescriptions;//device category descriptions
    public HashMap<String,String> zwNetOps;//network operations
    public HashMap<String,String> zwNetOpsDescriptions;//network operations descriptions
    public HashMap<String,String> zwGenericDevClassDescriptions;//Generic device class types
    public HashMap<String,String> zwSpecificDevClassDescriptions;//Specific device class types
    public HashMap<String,String> zwInterfaceCommands;//zwif commands
    public HashMap<String,String> zwErrorCodes;//zw error codes
    public HashMap<String,String> zwErrorDescriptions;//zw error descriptions

}




