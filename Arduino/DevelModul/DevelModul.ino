#include "FastLED.h"
#include <SoftwareSerial.h>

/////////////////////////
// Init Varaibles for the LED Strip
#define NUM_LEDS 64
#define DATA_PIN 2
CRGB leds[NUM_LEDS];

////////////////////////
// Control for the state machine
int activeMode = 0;  //Currently Active Mode
int currColor = 0;   // Currently safed color
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

void loop(){
   delay(10);
   if (btSerial.available()){
      Serial.print("btData here");
      btData = btSerial.readString();
      
      Serial.print(btData);
      activeMode = btData.toInt();
      btData="";
   }
   
   if(!idle){
    switch(activeMode){
    case 0:
        turnOffAllLeds(false);
        leds[0] = CRGB::Red;
        FastLED.show();
      break;
    case 1:
        turnOffAllLeds(false);
        leds[1] = CRGB::Red;
        FastLED.show();
      break;
    case 2:
        turnOffAllLeds(false);
        leds[2] = CRGB::Red;
        FastLED.show();
      break;
    case 3:
        turnOffAllLeds(false);
        leds[3] = CRGB::Red;
        FastLED.show();
      break;
    case 4:
        turnOffAllLeds(false);
        leds[4] = CRGB::Red;
        FastLED.show();
      break;
    case 5:
        turnOffAllLeds(false);
        leds[5] = CRGB::Red;
        FastLED.show();
      break;
    case 6:
        turnOffAllLeds(false);
        leds[6] = CRGB::Red;
        FastLED.show();
      break;
    case 7:
        turnOffAllLeds(false);
        leds[7] = CRGB::Red;
        FastLED.show();
      break;
    case 99:
        turnOffAllLeds(false);
        leds[8] = CRGB::Red;
        FastLED.show();
      break;
   }
    
  }

}



