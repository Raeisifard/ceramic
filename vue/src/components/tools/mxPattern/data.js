const type = "PATTERN";

function Config() {
  this.Array = [1, 2, 3];
  this.Boolean = true;
  this.Null=null;
  this.Number=123;
  this.Object={"a":"b","c":"d"};
  this.String = "Hello World";
}

function Setting() {

}

function JsonObject() {
  this.type = type.toLowerCase();
  this.xml = "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
    "  <block type=\"controls_if\" id=\"6eFRSf$hczGcECekJ5SG\" x=\"10\" y=\"382\">\n" +
    "    <value name=\"IF0\">\n" +
    "      <block type=\"logic_compare\" id=\"kHlxE(MxEmpPj/?%!*=P\">\n" +
    "        <field name=\"OP\">EQ</field>\n" +
    "        <value name=\"A\">\n" +
    "          <block type=\"math_arithmetic\" id=\"i}Z{evSzP[0r4!Vph9`A\">\n" +
    "            <field name=\"OP\">MULTIPLY</field>\n" +
    "            <value name=\"A\">\n" +
    "              <block type=\"math_number\" id=\"4iVGu#e{urLODQ1|%~ds\">\n" +
    "                <field name=\"NUM\">6</field>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "            <value name=\"B\">\n" +
    "              <block type=\"math_number\" id=\"%,4JUR]2C$M0@dndn9rt\">\n" +
    "                <field name=\"NUM\">7</field>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "          </block>\n" +
    "        </value>\n" +
    "        <value name=\"B\">\n" +
    "          <block type=\"math_number\" id=\"dP8o`m5?7O:^$giBP=0^\">\n" +
    "            <field name=\"NUM\">42</field>\n" +
    "          </block>\n" +
    "        </value>\n" +
    "      </block>\n" +
    "    </value>\n" +
    "    <statement name=\"DO0\">\n" +
    "      <block type=\"controls_if\" id=\"-`SD=(nAZBx_Q$mh0~{D\">\n" +
    "        <value name=\"IF0\">\n" +
    "          <block type=\"logic_compare\" id=\"U@EmEBj;U}_5xa3lCyns\">\n" +
    "            <field name=\"OP\">EQ</field>\n" +
    "            <value name=\"A\">\n" +
    "              <block type=\"math_arithmetic\" id=\".GTA#rMg2By!9q6Upw/6\">\n" +
    "                <field name=\"OP\">MULTIPLY</field>\n" +
    "                <value name=\"A\">\n" +
    "                  <block type=\"math_number\" id=\"dsKOgiB.?)=jsWs*xeTl\">\n" +
    "                    <field name=\"NUM\">6</field>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "                <value name=\"B\">\n" +
    "                  <block type=\"math_number\" id=\"S}SMCJ|L1)56[;};G*Fp\">\n" +
    "                    <field name=\"NUM\">7</field>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "            <value name=\"B\">\n" +
    "              <block type=\"math_number\" id=\"Xl~:JoL+LsFh8L(GJK|s\">\n" +
    "                <field name=\"NUM\">42</field>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "          </block>\n" +
    "        </value>\n" +
    "        <statement name=\"DO0\">\n" +
    "          <block type=\"controls_if\" id=\"+|er5_H(Cda6G`)[Q.0/\">\n" +
    "            <value name=\"IF0\">\n" +
    "              <block type=\"logic_compare\" id=\";*25|?C-nFzty6oU}d(:\">\n" +
    "                <field name=\"OP\">EQ</field>\n" +
    "                <value name=\"A\">\n" +
    "                  <block type=\"math_arithmetic\" id=\"csEkZXr.*([x2x[D#H^K\">\n" +
    "                    <field name=\"OP\">MULTIPLY</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_number\" id=\"`4LYi[Bs[Kg!7G#LUS]]\">\n" +
    "                        <field name=\"NUM\">6</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"{Kb?#a58709:7lsn9a-r\">\n" +
    "                        <field name=\"NUM\">7</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "                <value name=\"B\">\n" +
    "                  <block type=\"math_number\" id=\"=l}44SNd/8Xkej:]#nsX\">\n" +
    "                    <field name=\"NUM\">42</field>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "            <statement name=\"DO0\">\n" +
    "              <block type=\"controls_if\" id=\"njnqk]F+B4whU|2uLJg}\">\n" +
    "                <value name=\"IF0\">\n" +
    "                  <block type=\"logic_compare\" id=\"{SJI@ypz6@:@yI4fo]|H\">\n" +
    "                    <field name=\"OP\">EQ</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_arithmetic\" id=\",$Y[[zRpI42x|HrmKa/i\">\n" +
    "                        <field name=\"OP\">MULTIPLY</field>\n" +
    "                        <value name=\"A\">\n" +
    "                          <block type=\"math_number\" id=\"R`V8lR]QAEO:+9Jik/,E\">\n" +
    "                            <field name=\"NUM\">6</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                        <value name=\"B\">\n" +
    "                          <block type=\"math_number\" id=\"_KJI`e9l8|g+CWo#O|I9\">\n" +
    "                            <field name=\"NUM\">7</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"rTSK7zmkCj^]H)pZmp)6\">\n" +
    "                        <field name=\"NUM\">42</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </statement>\n" +
    "            <next>\n" +
    "              <block type=\"controls_if\" id=\"idNd4pY!eSjWKsy*TR`M\">\n" +
    "                <value name=\"IF0\">\n" +
    "                  <block type=\"logic_compare\" id=\"H#:}dya.yfb*(-qL$Us7\">\n" +
    "                    <field name=\"OP\">EQ</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_arithmetic\" id=\"yVB@^xg8`,%o/8U_v^?m\">\n" +
    "                        <field name=\"OP\">MULTIPLY</field>\n" +
    "                        <value name=\"A\">\n" +
    "                          <block type=\"math_number\" id=\"e6+y$-o]KgQs5d]HQ3i;\">\n" +
    "                            <field name=\"NUM\">6</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                        <value name=\"B\">\n" +
    "                          <block type=\"math_number\" id=\"Xr7cMY17?8p=CPp;f+F0\">\n" +
    "                            <field name=\"NUM\">7</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"msWzSYTJMp;Tdu,g5]*.\">\n" +
    "                        <field name=\"NUM\">42</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </next>\n" +
    "          </block>\n" +
    "        </statement>\n" +
    "        <next>\n" +
    "          <block type=\"controls_if\" id=\"2jY+jfYxpWW[1@I4$3d/\">\n" +
    "            <value name=\"IF0\">\n" +
    "              <block type=\"logic_compare\" id=\"=$9Lm,;|yfWOUI,G733f\">\n" +
    "                <field name=\"OP\">EQ</field>\n" +
    "                <value name=\"A\">\n" +
    "                  <block type=\"math_arithmetic\" id=\"O1+awi0e^sB]/wAmv=j8\">\n" +
    "                    <field name=\"OP\">MULTIPLY</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_number\" id=\"kvW_?q$[dO1#AdUu0QBX\">\n" +
    "                        <field name=\"NUM\">6</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"5%;5+{)qA}4i[b_t{x+w\">\n" +
    "                        <field name=\"NUM\">7</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "                <value name=\"B\">\n" +
    "                  <block type=\"math_number\" id=\"]cC)uGGK@}f6,bfoI-7o\">\n" +
    "                    <field name=\"NUM\">42</field>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "            <statement name=\"DO0\">\n" +
    "              <block type=\"controls_if\" id=\"[Id~)2drJCj,]j9vcD)S\">\n" +
    "                <value name=\"IF0\">\n" +
    "                  <block type=\"logic_compare\" id=\"bovh,w|!,o_7)hs#=Ui6\">\n" +
    "                    <field name=\"OP\">EQ</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_arithmetic\" id=\"7#Tk;O.(C(Mx9n3AUu^u\">\n" +
    "                        <field name=\"OP\">MULTIPLY</field>\n" +
    "                        <value name=\"A\">\n" +
    "                          <block type=\"math_number\" id=\"L?[c%_CD`C5b3?T+I]HF\">\n" +
    "                            <field name=\"NUM\">6</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                        <value name=\"B\">\n" +
    "                          <block type=\"math_number\" id=\"-!T[+[AO?v%cx.a[Sh4N\">\n" +
    "                            <field name=\"NUM\">7</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"lFUL_E-5HFV~%p7O]%P(\">\n" +
    "                        <field name=\"NUM\">42</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </statement>\n" +
    "            <next>\n" +
    "              <block type=\"controls_if\" id=\"3?GSC4UVu+nGAzJbw#jb\">\n" +
    "                <value name=\"IF0\">\n" +
    "                  <block type=\"logic_compare\" id=\"-@,;f=;*]joznhj^c]qW\">\n" +
    "                    <field name=\"OP\">EQ</field>\n" +
    "                    <value name=\"A\">\n" +
    "                      <block type=\"math_arithmetic\" id=\"G/_zDAu8Tk,DzPU8pxI_\">\n" +
    "                        <field name=\"OP\">MULTIPLY</field>\n" +
    "                        <value name=\"A\">\n" +
    "                          <block type=\"math_number\" id=\"j4Zw7bTXlnyP_$7M$%uu\">\n" +
    "                            <field name=\"NUM\">6</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                        <value name=\"B\">\n" +
    "                          <block type=\"math_number\" id=\"bNT*H[4.k|OnY$EI?+/n\">\n" +
    "                            <field name=\"NUM\">7</field>\n" +
    "                          </block>\n" +
    "                        </value>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                    <value name=\"B\">\n" +
    "                      <block type=\"math_number\" id=\"bZI)y?-RfZ-6WXk@~@aV\">\n" +
    "                        <field name=\"NUM\">42</field>\n" +
    "                      </block>\n" +
    "                    </value>\n" +
    "                  </block>\n" +
    "                </value>\n" +
    "              </block>\n" +
    "            </next>\n" +
    "          </block>\n" +
    "        </next>\n" +
    "      </block>\n" +
    "    </statement>\n" +
    "  </block>\n" +
    "  <block type=\"controls_if\" id=\"v/`)h{EuOi6cgm5|SRc)\" x=\"10\" y=\"16250\">\n" +
    "    <value name=\"IF0\">\n" +
    "      <block type=\"logic_compare\" id=\"A`2%/f%X7a^xwes;`z?3\">\n" +
    "        <field name=\"OP\">EQ</field>\n" +
    "        <value name=\"A\">\n" +
    "          <block type=\"math_arithmetic\" id=\"e,$F}k/WvG^IvG1eJ(T9\">\n" +
    "            <field name=\"OP\">MULTIPLY</field>\n" +
    "            <value name=\"A\">\n" +
    "              <block type=\"math_number\" id=\"r$,I(U}*WxQp)lCJda33\">\n" +
    "                <field name=\"NUM\">6</field>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "            <value name=\"B\">\n" +
    "              <block type=\"math_number\" id=\"#0xV6qMBj%hA6SnRO=W1\">\n" +
    "                <field name=\"NUM\">7</field>\n" +
    "              </block>\n" +
    "            </value>\n" +
    "          </block>\n" +
    "        </value>\n" +
    "        <value name=\"B\">\n" +
    "          <block type=\"math_number\" id=\"^QODLtLNf,c^5RM;x,_8\">\n" +
    "            <field name=\"NUM\">42</field>\n" +
    "          </block>\n" +
    "        </value>\n" +
    "      </block>\n" +
    "    </value>\n" +
    "  </block>\n" +
    "</xml>";
  this.lang = "freemaker";
 /* this.kind = "trigger";
  this.state = "false";
  this.period = 0;
  this.delay = 0;*/
  this.config = new Config();
  this.setting = new Setting();
}

export default JsonObject;