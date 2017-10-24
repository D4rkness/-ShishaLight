#include "FastLED.h"
#include <SoftwareSerial.h>
#include <ArduinoJson.h>

/////////////////////////
// Init Varaibles for the LED Strip
#define NUM_LEDS 64
#define DATA_PIN 2
CRGB leds[NUM_LEDS];

////////////////////////
// Control for the state machine
int activeMode = 0;  //Currently Active Mode
int accRed = 0;
int accBlue = 110;
int accGreen = 0;
int paintingArr[NUM_LEDS]; // Array that holds every 64 Colors from the Painting transmitted from the app
boolean colorUpdated = false; // Flag for the new color synchronisation
boolean idle = false; // Flag to set the State Machine to idle, when a Mode with no Motion is active

///////////////////////
// Pins for the Bluetooth Modul
#define rxPin 10
#define txPin 11
SoftwareSerial btSerial(rxPin, txPin);
String btData;

///////////////////////
// Specific Valus for the Modis

//////////////////////
// RainbowMode
uint8_t delayRainbow = 10;                                        // A delay value for the sequence(s)
uint8_t hueRainbow = 0;                                          // Starting hue value.
uint8_t deltahueRainbow = 1;                                        // Hue change between pixels.

//////////////////////
// Loading
uint8_t loadingSpeed = 40;  
uint8_t loadingCounter = 0;
boolean loadingBlackFlag = false;

/////////////////////
// Running 
uint8_t runningSpeed = 40;  
uint8_t runningLenght = 15;
uint8_t runningCounter = 0;

//////////////////////
// Lightning
uint8_t lightningStart;
uint8_t lightningLenght;


void turnOffAllLeds(boolean showLedsFlag){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB::Black; 
  }
  if(showLedsFlag){
    FastLED.show();
  }
}

void bootAnimation(){
  for(int i = 0; i < NUM_LEDS; i++){
    leds[i] = CRGB::Red;
    FastLED.show();
    delay(20);
  }
  turnOffAllLeds(true);
}

void runningAnim(){
  Serial.println(runningCounter);

  if(runningCounter>NUM_LEDS-1){
    runningCounter=0;
    leds[runningCounter]= CRGB::Black; 
     for(int i = 1; i < runningLenght+1; i++){
     leds[runningCounter+i]= CRGB( accRed, accGreen, accBlue); 
     }
     runningCounter++;
  }else if(runningCounter+runningLenght>NUM_LEDS-1 && runningCounter<NUM_LEDS+runningLenght-1){
    leds[runningCounter]= CRGB::Black; 
    for(int i = 1; i < runningLenght+1; i++){
     leds[(runningCounter+i)%(NUM_LEDS-1)]= CRGB( accRed, accGreen, accBlue); 
     }
     runningCounter++;
  }else{
    leds[runningCounter]= CRGB::Black; 
     for(int i = 1; i < runningLenght+1; i++){
     leds[runningCounter+i]= CRGB( accRed, accGreen, accBlue); 
     }
     runningCounter++;
  }
    
  
  FastLED.show();
  
}


void rainbow_march() {                                        // The fill_rainbow call doesn't support brightness levels
  hueRainbow++;                                                  // Increment the starting hue.
  fill_rainbow(leds, NUM_LEDS, hueRainbow, deltahueRainbow);            // Use FastLED's fill_rainbow routine.
}


void loadingAnim(){
  
  if(!loadingBlackFlag){
    leds[loadingCounter]= CRGB( accRed, accGreen, accBlue);
    loadingCounter++;
  }else{
    leds[loadingCounter]= CRGB::Black;
    loadingCounter++;
  }

  if(loadingCounter>= NUM_LEDS){
    loadingBlackFlag= !loadingBlackFlag;
    loadingCounter = 0 ;
  }
  FastLED.show();     
}


void setup() { 
      FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
      for(int i = 0; i<NUM_LEDS; i++){
        paintingArr[i]=0;
      }
      bootAnimation();
      Serial.begin(9600);   
      Serial.println("Goodnight moon!");
      btSerial.begin(9600);
      btSerial.println("init");
}

void setAllLedsWithAccColors(){
  for(int i = 0; i<NUM_LEDS; i++){
        leds[i]= CRGB( accRed, accGreen, accBlue);
      }
      FastLED.show();
}

void loop(){
  delay(10);
   if (btSerial.available()){
      Serial.println("btData here");
      btData = btSerial.readString();
      StaticJsonBuffer<200> jsonBuffer;
      JsonObject& root = jsonBuffer.parseObject(btData);
      String command = root["command"];
      Serial.println(command);
      if(command=="c"){
        String value = root["value"];
        Serial.println(value);
        long number = (long) strtol( &value[0], NULL, 16);
        accRed= number >> 16;
        accGreen = number >> 8 & 0xFF;
        accBlue = number & 0xFF;
        Serial.println("Red");
        Serial.println(accRed);
        Serial.println("Green");
        Serial.println(accGreen);
        Serial.println("Blue");
        Serial.println(accBlue);
      }else if(command=="m"){
        int value = root["mode"];
        Serial.println(value);
        activeMode = value;
      }
      btData="";
   }
   
   if(!idle){
    switch(activeMode){
    case 0:
        setAllLedsWithAccColors();
        break;
    case 1:
        EVERY_N_MILLISECONDS(delayRainbow) {                           // FastLED based non-blocking routine to update/display the sequence.
          rainbow_march();
         }
         show_at_max_brightness_for_power();  
      break;
    case 2:
        turnOffAllLeds(false);
        leds[2] =  CRGB( accRed, accGreen, accBlue);
        FastLED.show();
      break;
    case 3:
        EVERY_N_MILLISECONDS(loadingSpeed) {                           // FastLED based non-blocking routine to update/display the sequence.
          loadingAnim();
         }
      break;
    case 4:
        EVERY_N_MILLISECONDS(runningSpeed) {                           // FastLED based non-blocking routine to update/display the sequence.
         runningAnim();
         }
      break;
    case 5:
        turnOffAllLeds(false);
        leds[5] =  CRGB( accRed, accGreen, accBlue);
        FastLED.show();
      break;
    case 6:
        turnOffAllLeds(false);
        leds[6] =  CRGB( accRed, accGreen, accBlue);
        FastLED.show();
      break;
    case 7:
        turnOffAllLeds(false);
        leds[7] =  CRGB( accRed, accGreen, accBlue);
        FastLED.show();
      break;
    case 99:
        turnOffAllLeds(false);
        leds[8] =  CRGB( accRed, accGreen, accBlue);
        FastLED.show();
      break;
   }
    
  }

}



