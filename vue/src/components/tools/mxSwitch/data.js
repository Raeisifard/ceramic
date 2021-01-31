const type = "SWITCH";

function TriggerConfig() {
    this.kind = "trigger";
    this.switchName = "";
}

function PushConfig() {
    this.kind = "push";
    this.switchName = "";
}

function RelayConfig() {
    this.kind = "relay";
    this.switchName = "";
}

function TriggerSetting() {
    this._ = {};
    this._.state = false;
    this._.period = 0;
    this._.delay = 0;
}

function PushSetting() {
}

function RelaySetting() {
    this._ = {};
    this._.state = true;
}

function JsonObject(kind) {
    this.type = type.toLowerCase();
    if (kind == 'trigger') {
        this.config = new TriggerConfig();
        this.setting = new TriggerSetting();
    } else if (kind == 'relay') {
        this.config = new RelayConfig();
        this.setting = new RelaySetting();
    } else if (kind == 'push') {
        this.config = new PushConfig();
        this.setting = new PushSetting();
    }
}

export default JsonObject;