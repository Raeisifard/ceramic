<template>
    <Panel style="height: 100%">
        <template slot="header">
            <div class="f-row">
                <div class="f-full">
                    <ButtonGroup selectionMode="single">
                        <LinkButton iconCls="" :toggle="true" :selected="trigger"
                                    @click="switchKindChanged('trigger'); switchNameChanged('switchButtons')">Trigger
                        </LinkButton>
                        <LinkButton iconCls="" :toggle="true" :selected="relay"
                                    @click="switchKindChanged('relay'); switchNameChanged('LightGreenToggle')">Relay
                        </LinkButton>
                        <LinkButton iconCls="" :toggle="true" :selected="push"
                                    @click="switchKindChanged('push'); switchNameChanged('RoundCssBrownPushButton')">
                            Push
                        </LinkButton>
                    </ButtonGroup>
                    <LinkButton style="float: right;" iconCls="icon-help" :plain="true">Help</LinkButton>
                </div>
            </div>
        </template>
        <div class="f-full" style="height: 100%">
            <div v-show="trigger" class="grid-container" style="height: 180px">
                <!--<div :class="{'grid-item': true, 'selected': isSelected === 'SwitchButtons'}"
                     @click="switchNameChanged('SwitchButtons')">
                    <switch-buttons @click="switchNameChanged('SwitchButtons')"
                                    :value="true"></switch-buttons>
                </div>-->
                <!--<div :class="{'grid-item': true, 'selected': isSelected === 'BlackWhiteYesNo'}"
                     @click="switchNameChanged('BlackWhiteYesNo')">
                    <black-white-yes-no @click="switchNameChanged('BlackWhiteYesNo')"
                                              :value="true"></black-white-yes-no>
                </div>-->
               <!-- <div :class="{'grid-item': true, 'selected': isSelected === 'BlueSubscribeButtons'}"
                     @click="switchNameChanged('BlueSubscribeButtons')">
                    <blue-red-yes-no-rounded @click="switchNameChanged('BlueSubscribeButtons')"
                                            :value="true"></blue-red-yes-no-rounded>
                </div>-->
                <div :class="{'grid-item': true, 'selected': isSelected === 'RedGreenToggleSwitch'}"
                     @click="switchNameChanged('RedGreenToggleSwitch')">
                    <red-green-toggle-switch @click="switchNameChanged('RedGreenToggleSwitch')"
                                                    :value="true"></red-green-toggle-switch>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'GreenGrayOnOffBreak'}"
                     @click="switchNameChanged('GreenGrayOnOffBreak')">
                    <green-gray-on-off-break @click="switchNameChanged('GreenGrayOnOffBreak')"
                                                    :value="true"></green-gray-on-off-break>
                </div>
                <!--<div :class="{'grid-item': true, 'selected': isSelected === 'EasyUiRoundedSwitchButtons'}"
                     @click="switchNameChanged('EasyUiRoundedSwitchButtons')">
                    <easy-ui-rounded-switch-buttons @click="switchNameChanged('EasyUiRoundedSwitchButtons')"
                                                    :value="true"></easy-ui-rounded-switch-buttons>
                </div>-->
                <div :class="{'grid-item': true, 'selected': isSelected === 'SwitchButton3d'}"
                     @click="switchNameChanged('SwitchButton3d')">
                    <switch-button3-d @click="switchNameChanged('SwitchButton3d')"
                                      :value="true"></switch-button3-d>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'BlueSubscribeButtons'}"
                     @click="switchNameChanged('BlueSubscribeButtons')">
                    <blue-subscribe-buttons @click="switchNameChanged('BlueSubscribeButtons')"
                                            :value="true"></blue-subscribe-buttons>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'OrangeSubscribeButtons'}"
                     @click="switchNameChanged('OrangeSubscribeButtons')">
                    <orange-subscribe-buttons @click="switchNameChanged('OrangeSubscribeButtons')"
                                              :value="true"></orange-subscribe-buttons>
                </div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
            </div>
            <div v-show="push" class="f-full grid-container" style="height:  180px">
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssBrownPushButton'}"
                     @click="switchNameChanged('RoundCssBrownPushButton')">
                    <round-css-brown-push-button></round-css-brown-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssGrayPushButton'}"
                     @click="switchNameChanged('RoundCssGrayPushButton')">
                    <round-css-gray-push-button></round-css-gray-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssGreenPushButton'}"
                     @click="switchNameChanged('RoundCssGreenPushButton')">
                    <round-css-green-push-button></round-css-green-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssNavyPushButton'}"
                     @click="switchNameChanged('RoundCssNavyPushButton')">
                    <round-css-navy-push-button></round-css-navy-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssOrangePushButton'}"
                     @click="switchNameChanged('RoundCssOrangePushButton')">
                    <round-css-orange-push-button></round-css-orange-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssPinkPushButton'}"
                     @click="switchNameChanged('RoundCssPinkPushButton')">
                    <round-css-pink-push-button></round-css-pink-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssPurplePushButton'}"
                     @click="switchNameChanged('RoundCssPurplePushButton')">
                    <round-css-purple-push-button></round-css-purple-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssRedPushButton'}"
                     @click="switchNameChanged('RoundCssRedPushButton')">
                    <round-css-red-push-button></round-css-red-push-button>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'RoundCssYellowPushButton'}"
                     @click="switchNameChanged('RoundCssYellowPushButton')">
                    <round-css-yellow-push-button></round-css-yellow-push-button>
                </div>
            </div>
            <div v-show="relay" class="f-full grid-container" style="height:  180px">
                <div :class="{'grid-item': true, 'selected': isSelected === 'LightGreenToggle'}"
                     @click="switchNameChanged('LightGreenToggle')">
                    <light-green-toggle :value="true"></light-green-toggle>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'IosGreenToggle'}"
                     @click="switchNameChanged('IosGreenToggle')">
                    <ios-green-toggle :value="true"></ios-green-toggle>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'SkewedGreenToggle'}"
                     @click="switchNameChanged('SkewedGreenToggle')">
                    <skewed-green-toggle :value="true"></skewed-green-toggle>
                </div>
                <div :class="{'grid-item': true, 'selected': isSelected === 'FlatGreenToggle'}"
                     @click="switchNameChanged('FlatGreenToggle')">
                    <flat-green-toggle :value="true"></flat-green-toggle>
                </div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
                <div class="grid-item"></div>
            </div>
        </div>
        <template slot="footer">
            <div style="padding:5px; display: flex; justify-content:space-around;">
                <LinkButton style="width:80px" @click="configOk">Ok</LinkButton>
                <LinkButton style="width:80px;" @click="configCancel">Cancel</LinkButton>
            </div>
        </template>
    </Panel>
</template>

<script>
  import { EventBus } from '../../../event-bus.js';
  //import SwitchButtons from './repo/switch/SwitchButtons.vue';
  //import BlackWhiteYesNo from './repo/switch/BlackWhiteYesNo.vue';
  import OrangeSubscribeButtons from "./repo/switch/orangeSubscribeButtons";
  import BlueSubscribeButtons from "./repo/switch/blueSubscribeButtons";
  import SwitchButton3D from "./repo/switch/SwitchButton3d";
  import EasyUiRoundedSwitchButtons from "./repo/switch/EasyUiRoundedSwitchButtons";
  import LightGreenToggle from "./repo/relay/LightGreenToggle";
  import IosGreenToggle from "./repo/relay/IosGreenToggle";
  import SkewedGreenToggle from "./repo/relay/SkewedGreenToggle";
  import FlatGreenToggle from "./repo/relay/FlatGreenToggle";
  import RoundCssNavyPushButton from "./repo/push/roundCssNavyPushButton";
  import RoundCssBrownPushButton from "./repo/push/roundCssBrownPushButton";
  import RoundCssGrayPushButton from "./repo/push/roundCssGrayPushButton";
  import RoundCssGreenPushButton from "./repo/push/roundCssGreenPushButton";
  import RoundCssOrangePushButton from "./repo/push/roundCssOrangePushButton";
  import RoundCssPurplePushButton from "./repo/push/roundCssPurplePushButton";
  import RoundCssPinkPushButton from "./repo/push/roundCssPinkPushButton";
  import RoundCssRedPushButton from "./repo/push/roundCssRedPushButton";
  import RoundCssYellowPushButton from "./repo/push/roundCssYellowPushButton";
  import GreenGrayOnOffBreak from "./repo/switch/GreenGrayOnOffBreak";
  import RedGreenToggleSwitch from "./repo/switch/RedGreenToggleSwitch";
  //import BlueRedYesNoRounded from "./repo/switch/BlueRedYesNoRounded";

  export default {
    name: "dialogConfig",
    components: {
      RedGreenToggleSwitch,
      GreenGrayOnOffBreak,
      //BlueRedYesNoRounded,
      RoundCssYellowPushButton,
      RoundCssRedPushButton,
      RoundCssPinkPushButton,
      RoundCssPurplePushButton,
      RoundCssOrangePushButton,
      RoundCssGreenPushButton,
      RoundCssBrownPushButton,
      RoundCssGrayPushButton,
      RoundCssNavyPushButton,
      FlatGreenToggle,
      SkewedGreenToggle,
      IosGreenToggle,
      LightGreenToggle,
      EasyUiRoundedSwitchButtons,
      SwitchButton3D,
      //SwitchButtons,
      //BlackWhiteYesNo,
      OrangeSubscribeButtons,
      BlueSubscribeButtons
    },
    props: ["messageHandler"],
    data() {
      return {
        isSelected: 'RedGreenToggleSwitch',
        cell: null,
        store: this.$store,
        kind: "trigger",
        trigger: true,
        relay: false,
        push: false,
      }
    },
    methods: {
      configOk(evt) {
        this.messageHandler("SET_SWITCH_CONFIG", {kind:this.kind, switchName:this.isSelected});
      },
      configCancel(evt) {
        this.messageHandler("CLOSE_DIALOG_CONFIG", {kind:this.kind, switchName:this.isSelected});
      },
      switchKindChanged(kind) {
        this.kind = kind;
        switch (this.kind) {
          case "trigger":
            this.trigger = true;
            this.relay = false;
            this.push = false;
            break;
          case "relay":
            this.trigger = false;
            this.relay = true;
            this.push = false;
            break;
          case "push":
            this.trigger = false;
            this.relay = false;
            this.push = true;
            break;
        }
      },
      switchNameChanged(name) {
        this.isSelected = name;
      }
    },
    mounted() {
      switch (this.kind) {
        case "trigger":
          break;
        case "relay":
          this.relay = true;
          this.trigger = false;
          this.push = false;
          break;
        case "push":
          this.push = true;
          this.trigger = false;
          this.relay = false;
          break;
        default:
          break;
      }
    },
    computed: {
      console: () => console,
      window: () => window,
    }
  }
</script>

<style scoped>
    .grid-container {
        display: grid;
        grid-template-columns: 33% 33.5% 33%;
    }

    .grid-item {
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px solid rgba(0, 0, 0, 0.8);
        height: 60px;
        /*font-size: 30px;*/
        text-align: center;
    }

    .selected {
        background-color: #52d689;
    }
</style>