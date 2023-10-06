
import pyaudio
import speech_recognition as sr
import pyttsx3;


recognizer = sr.Recognizer()

try:
        with sr.Microphone() as mic:

            recognizer.adjust_for_ambient_noise(mic, duration=0.2)
            print("Escuchando...")
            
            engine = pyttsx3.init()
            rate = engine.getProperty('rate')
            engine.setProperty('rate', rate-30)
            engine.say("Hable ahora")
            engine.runAndWait()
            
            audio = recognizer.listen(mic)
            text = recognizer.recognize_google(audio, language='es-EC')
            text = text.lower()
            archivo = open("respuesta.txt","w")
            archivo.write(text)
            archivo.close()

            print(f"Texto reconocido: {text}")

except sr.UnknownValueError():
        text = text.lower()
        archivo = open("respuesta.txt","w")
        archivo.write("No se encontró audio")
        print(f"Texto reconocido: {text}")
        input()