% =========================================================================
% =========================================================================
% ============ PROGRAMMA DI ANALISI DELLA VULNERABILITA' ==================
% ============     IDRAULICA DEL PONTE DI BORGOFORTE     ==================
% =========================================================================
% =========================================================================

function varargout = SW_Borgoforte(varargin)
% SW_BORGOFORTE M-file for SW_Borgoforte.fig
% SW_BORGOFORTE, by itself, creates a new SW_BORGOFORTE or raises the existing
% singleton*.
%
% H = SW_BORGOFORTE returns the handle to a new SW_BORGOFORTE or the handle to
% the existing singleton*.
%
% SW_BORGOFORTE('CALLBACK',hObject,eventData,handles,...) calls the local
% function named CALLBACK in SW_BORGOFORTE.M with the given input arguments.
%
% SW_BORGOFORTE('Property','Value',...) creates a new SW_BORGOFORTE or raises the
% existing singleton*.  Starting from the left, property value pairs are
% applied to the GUI before SW_Borgoforte_OpeningFcn gets called.  An
% unrecognized property name or invalid value makes property application
% stop.  All inputs are passed to SW_Borgoforte_OpeningFcn via varargin.
%
% *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
% instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help SW_Borgoforte

% Last Modified by GUIDE v2.5 17-Oct-2013 19:52:33

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @SW_Borgoforte_OpeningFcn, ...
                   'gui_OutputFcn',  @SW_Borgoforte_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT



%==========================================================================
%==========================================================================
%====== OPERAZIONI ESEGUITE PRIMA DELL'ESECUZIONE DEL PROGRAMMA ===========
%==========================================================================
%==========================================================================
% elenco delle operazioni che il programma esegue prima di interagire con
% l'utente..

function SW_Borgoforte_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to SW_Borgoforte (see VARARGIN)

% Choose default command line output for SW_Borgoforte
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

clc

%==========================================================================
%======================DEFINIZIONE VARIABILI GLOBALI=======================
%==========================================================================
% variabili per la gestione della data
global datagiorno; global datamese; global dataanno;
% variabili per la direcotry contenente i file da elaborare
global cod_prog; global arrivo; global cod_setup;
% variabili
global minutick; global minutistr; global orack; global orastr;
global ck_setup; global setup;
% per checkbox TEL MANTOVA/MODENA
global chkMA; global chkMO;
%==========================================================================
%============================= SETUP INIZIALI =============================
%==========================================================================
% settaggio assi dei grafici e parametri riferiti alla visualizzazione
axes(handles.axes1); promo_y=linspace(0,3,4); promo_x=linspace(0,24,25);
axis([0 24 0 3]);
% settaggio assi grafico coefficiente di sicurezza
set(handles.axes1,'XLim',[0 24]); set(handles.axes1,'YLim',[0 3]);
set(handles.axes1,'XTick',promo_x); set(handles.axes1,'YTick',promo_y);
xlabel('ore della giornata'); ylabel('coeff. di sicurezza');
%==========================================================================
%=============================== DIRECTORY ================================
%==========================================================================
% impostazione della directory del programma e del file setup
cod_prog='D:\Borgoforte\Programma Matlab Borgoforte';
cod_setup='D:\Borgoforte\Setup';
%==========================================================================
%===========ACQUISIZIONE E VISUALIZZAZIONE DELLA DATA ODIERNA==============
%==========================================================================
% visualizzazione e acquisizione della data odierna, 
data=date; tempo=clock; orack=tempo(1,4); minutick=tempo(1,5);
orastr=num2str(orack); minutistr=num2str(minutick);
% acquisizione separata del giorno, mese, anno
datagiorno=data(1:2); datamese=data(4:6); dataanno=data(8:11);
% il mese è in lettere (Gen,Feb,...). Trasformazione in stringa numero
switch datamese
    case 'Jen'
        datamese='01';
    case 'Feb'
        datamese='02';
    case 'Mar'
        datamese='03';
    case 'Apr'
        datamese='04';
    case 'May'
        datamese='05';
    case 'Jun'
        datamese='06';
    case 'Jul'
        datamese='07';
    case 'Aug'
        datamese='08';
    case 'Sep'
        datamese='09';
    case 'Oct'
        datamese='10';
    case 'Nov'
        datamese='11';
    case 'Dec'
        datamese='12';
end
% creazione della stringa di visualizzazione
dataodierna=strcat(datagiorno,'/',datamese,'/',dataanno);
set(handles.text2,'string',dataodierna);
% se i minuti sono < 10 inserisco uno zero davanti, per una più corretta
% visualizzazione, anche per l'utente
if minutick<10
    oraodierna=strcat(orastr,':','0',minutistr);
else
    oraodierna=strcat(orastr,':',minutistr);
end
set(handles.text9,'string',oraodierna);
%==========================================================================
%============== DETERMINAZIONE DEL GIORNO DELLA SETTIMANA =================
%==========================================================================
% il giorno della settimana è fondamentale per il cambio d'ora (ora
% legale). I dati di Borgoforte sono riferiti ad un tempo assoluto, quindi 
% oltre alla traslazione di un'ora (+1) per l'italia abbiamo anche l'ora 
% legale da considerare. Per sapere esattamente quando entra in gioco 
% dobbiamo sapere il giorno della settimana.
% Ultima domenica di Marzo:   +1 (alle 02:00 --> 03:00)
% Ultima domenica di Ottobre: -1 (alle 03:00 --> 02:00)

% consideriamo una data di riferimento: 1-gen-1928, DOMENICA
dataannonum=str2num(dataanno); d=dataannonum-1928;
% divido annodifferenza per 4 ottenedo il quoziente e il resto
quoz=fix(d/4); resto=mod(d,4);
if resto==0
    d=d+quoz;
else
    d=d+quoz+1;
end
resto_d=mod(d,7);
% il primo passo è la determinazione dell giorno della settimana del primo
% giorno dell'anno scelto dall'utente. Creao una corrisponde biunivoca tra
% i giorni della settimana e dei numeri progressivi.
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch resto_d
    case 0
        ggprimoanno='Dom'; % 0=domenica
    case 1
        ggprimoanno='Lun'; % 1=lunedì
    case 2
        ggprimoanno='Mar'; % 2=martedì
    case 3
        ggprimoanno='Mer'; % 3=mercoledì
    case 4
        ggprimoanno='Gio'; % 4=giovedì
    case 5
        ggprimoanno='Ven'; % 5=venerdì
    case 6
        ggprimoanno='Sab'; % 6=sabato
end
%==========================================================================
% il secondo passo è la determinazione del giorno della settinama del primo
% giorno del mese scelto dall'utente.
% La tabella di riferimento che ho trovato in un esecizio in internet 
% associa il numero al giorno della settimana come la convenzione appena 
% citata (0=Dom, ecc...).

%  num     giorno    anno non bisestile       anno bisestile
%   0       Dom          Gen Ott              Gen Apr Lug
%   1       Lun          Mag                  Ott
%   2       Mar          Ago                  Mag
%   3       Mer          Feb Mar Nov          Feb Ago
%   4       Gio          Giu                  Mar Nov
%   5       Ven          Set Dic              Giu
%   6       Sab          Apr Lug              Set Dic

% creazione della tabella
tabellamesi(1,1)=1;   tabellamesi(1,2)=0;  tabellamesi(1,3)=0;
tabellamesi(2,1)=2;   tabellamesi(2,2)=3;  tabellamesi(2,3)=3;
tabellamesi(3,1)=3;   tabellamesi(3,2)=3;  tabellamesi(3,3)=4;
tabellamesi(4,1)=4;   tabellamesi(4,2)=6;  tabellamesi(4,3)=0;
tabellamesi(5,1)=5;   tabellamesi(5,2)=1;  tabellamesi(5,3)=2;
tabellamesi(6,1)=6;   tabellamesi(6,2)=4;  tabellamesi(6,3)=5;
tabellamesi(7,1)=7;   tabellamesi(7,2)=6;  tabellamesi(7,3)=0;
tabellamesi(8,1)=8;   tabellamesi(8,2)=2;  tabellamesi(8,3)=3;
tabellamesi(9,1)=9;   tabellamesi(9,2)=5;  tabellamesi(9,3)=6;
tabellamesi(10,1)=10; tabellamesi(10,2)=0; tabellamesi(10,3)=1;
tabellamesi(11,1)=11; tabellamesi(11,2)=3; tabellamesi(11,3)=4;
tabellamesi(12,1)=12; tabellamesi(12,2)=5; tabellamesi(12,3)=6;
% questa tabella consente di associare direttamente il giorno della
% settimana del primo giorno del generico mese se il primo giorno dell'anno
% cade in domenica (1/1/1928). Se vogliamo trasformarla in una tabella
% dinamica dobbiamo intrdurre un coefficiente che trasli i giorni in base al
% giorno della settimana con cui comincia l'anno corrente.
global tabellamesitraslata;
for i=1:12
    tabellamesitraslata(i,1)=tabellamesi(i,1);
    tabellamesitraslata(i,2)=tabellamesi(i,2)+resto_d;
    tabellamesitraslata(i,3)=tabellamesi(i,3)+resto_d;
    if tabellamesitraslata(i,2)>6
        tabellamesitraslata(i,2)=tabellamesitraslata(i,2)-7;
    end
    if tabellamesitraslata(i,3)>6
        tabellamesitraslata(i,3)=tabellamesitraslata(i,3)-7;
    end
end
% considerando che ci sono differenze a seconda che l'anno sia o meno
% bisestile, dobbiamo capire se l'anno scelto dall'utente è bisestile
if mod(dataannonum,4)==0 && (mod(dataannonum,100)~=0 || mod(dataannonum,400)==0)
    bisestile=1;
else
    bisestile=0;
end    
% utilizzo della colonna bisestile o no a seconda del valore di "bisestile"
datamesenum=str2num(datamese);
for i=1:12
    if datamesenum==tabellamesitraslata(i,1)
        primomese=tabellamesitraslata(i,2+bisestile);
    end
end
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch primomese    
    case 0
        ggprimomese='Dom'; % 0=domenica
    case 1
        ggprimomese='Lun'; % 1=lunedì
    case 2
        ggprimomese='Mar'; % 2=martedì
    case 3
        ggprimomese='Mer'; % 3=mercoledì
    case 4
        ggprimomese='Gio'; % 4=giovedì
    case 5
        ggprimomese='Ven'; % 5=venerdì
    case 6
        ggprimomese='Sab'; % 6=sabato
end
%==========================================================================
tabellagiorni(1,1)=1; tabellagiorni(1,2)=primomese;
for i=2:31
    tabellagiorni(i,1)=i;
    tabellagiorni(i,2)=tabellagiorni(i-1,2)+1;
    if tabellagiorni(i,2)==7
        tabellagiorni(i,2)=0;
    end
end
datagiornonum=str2num(datagiorno);
for i=1:31
    if datagiornonum==tabellagiorni(i,1)
        ggesatto=tabellagiorni(i,2);
    end
end
switch ggesatto
    case 0
        ggrichiesto='Dom'; % 0=domenica
    case 1
        ggrichiesto='Lun'; % 1=lunedì
    case 2
        ggrichiesto='Mar'; % 2=martedì
    case 3
        ggrichiesto='Mer'; % 3=mercoledì
    case 4
        ggrichiesto='Gio'; % 4=giovedì
    case 5
        ggrichiesto='Ven'; % 5=venerdì
    case 6
        ggrichiesto='Sab'; % 6=sabato
end
set(handles.text10,'string',ggrichiesto);
%==========================================================================
%====SETTAGGIO DI DEFAULT DEL PERCORSO DELLE CARTELLE DI GESTIONE FILE=====
%==========================================================================
% generazioni delle stringhe contenenti il percorso di default
arrivo=uigetdir;
% visualizzazione del percorso di dafault
set(handles.text3,'string',arrivo);
set(handles.text37,'string',cod_setup);
%numero di file presente nella cartela di arrivo e visualizzazione
cd(arrivo);
elencofile=dir;
nfile=size(elencofile,1)-2;
nfilestr=num2str(nfile);
set(handles.text7,'string',nfilestr);
% settaggio di dafualt delle caselle di checkbox: TEL. MODENA /TEL.MANTOVA
% TEL. MANTOVA (checkbox1)
set(handles.checkbox1,'Value',0);
chkMA=get(handles.checkbox1,'Value');
% TEL. MODENA (checkbox2)
set(handles.checkbox2,'Value',1);
chkMO=get(handles.checkbox2,'Value');
% Caricamento del file di setup
cd(cod_setup); ck_setup=0;
nome_setup='File Setup.txt';
ddd=dir;
for i=1:size(ddd,1)
    ck_setup=strcmp(nome_setup,ddd(i).name);
    if ck_setup==1
        setup=textread('File Setup.txt','%s');
        set(handles.radiobutton2,'Value',1);
    end
end
% settaggio directory codice programma
cd(cod_prog);

% UIWAIT makes SW_Borgoforte wait for user response (see UIRESUME)
% uiwait(handles.figure1);
% --- Outputs from this function are returned to the command line.
function varargout = SW_Borgoforte_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;




%==========================================================================
%==========================================================================
%================== CODICE IN SEQUENZA COME DA PROGRAMMA ==================
%==========================================================================
%==========================================================================




%==========================================================================
%======================= BLOCCO (0) cambio directory ======================
%==========================================================================
% CAMBIO CARTELLA =========================================================
% --- Executes on button press in pushbutton1. 
function pushbutton1_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global cod_prog;
global arrivo;
arrivo=uigetdir;
set(handles.text3,'String',arrivo);
set(handles.text3,'ForegroundColor',[1 0 0]);
cd(arrivo);
elencofile=dir;
nfile=size(elencofile,1)-2;
nfilestr=num2str(nfile);
set(handles.text7,'string',nfilestr);
set(handles.text7,'ForegroundColor',[1 0 0]);
set(handles.pushbutton1,'BackgroundColor',[0 1 0]);
cd(cod_prog);
%==========================================================================
%==========================================================================


%=========== BLOCCO (1) Analisi preliminare cartella di lavoro ============
%==========================================================================
% DATABASE.. ==============================================================
% Questa funzione crea un piccolo database virtuale, ovverosia analizza
% tutti i file nella cartella di arrivo e plotta a seconda del
% giorno/mese/anno quanti file trova corrispondenti alla generica giornata.
% Questo serve per l'utente che deve decidere il giorno di visualizzazione:
% in questo modo l'utente può verificare se nel giorno che vuole analizzare
% ci siano effettivamente dei dati da elaborare...
% --- Executes on button press in pushbutton5.
function pushbutton5_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton5 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

warning off
global arrivo;
global datagiorno; global datamese; global dataanno;
global minutick; global orack;
global tabellamesitraslata;
global mat_aneidro_definitiva; global mat_sonar_definitiva;
global mat_telmo; global mat_telma; global mat_check;

%==========================================================================
cd(arrivo); aa=dir; n=size(aa,1)-2;
%==========================================================================
% Conta deii singoli file presenti nella cartella 
aneidro=0; telma=0; telmo=0; check=0; sonar=0;
for i=3:n
    pezzofile=aa(i).name(1:2);
    switch pezzofile
        case 'an'
            aneidro=aneidro+1;
        case 'ma'
            telma=telma+1;
        case 'mo'
            telmo=telmo+1;
        case 'ro'
            check=check+1;
        case 'so'
            sonar=sonar+1;
    end
end
%==========================================================================
% Associazione della data al generico file. Innanzi tutto si deve
% uniformare il tipo di scala temporale da utilizzare. Anche se i file sono
% classificati secondo 4 tipologie differenti possono essere raggruppati in
% solo 2 categorie se consideriamo l'unità di misura temporale con cui sono
% salvati e inviati.
% Gruppo A: Telecamere e check. Il tempo rappresentato nella codifica del 
% nome del file è in orario Italiano, con l'impostazione automatica di 
% cambio ora legale. Basta solo leggere il nome del file.
% Gruppo B: Aneidro (nello stesso file sia anemometro che idrometro) 
% e sonar. Il tempo rappresentato nella codifica del nome del file è
% riferito al tempo "LABVIEW", ovverosia i secondi trascorsi
% dal 1 gennaio 1904 ore 00:00 (12:00 AM) ora assoluta (no fuso orario, 
% no ora legale)
% Prima della trasformazione del tempo labview in tempo reale dobbiamo fare
% alcuni passaggi.
%==========================================================================
% Preparazione per la conversione della codifica Labview ==================
% Partiamo dall'anno 2011, inutile prima in quanto non ci sono dati di
% Borgoforte. I secondi trascorsi fino all'inizio del 2011 sono considerati
% una costante, calcolati banalmente con Excel.......
% La tabella prevede in prima colonna l'anno di riferimento, nella seconda
% i secondi e nella terza una codifica che consenta di definire se l'anno è
% bisestile (1) o meno (0).
% Inserimento della prima riga

%    anno               secondi trascrosi      bisestile(1) / non(0)
tab_anni(1,1)=2011;  tab_anni(1,2)=3376681200;   tab_anni(1,3)=0;

% trasformo in numero la stringa contenete l'anno corrente
a=str2num(dataanno);

% All'anno corrente (quello calcolato in automatico dal programma) 
% sottraggo 2011 per sapere quanti anni il "ciclo for" deve generare 
% per determinare i secondi trascorsi dal 1 gennaio 1904 12:00 AM
% fin all'inizio dell'anno corrente. In questo modo la tabella è dinamica, 
% ovverosia si adatta alla data 
nanni=a-2011;
% ciclo for per la compilazione della tabella sopra descritta. La lunghezza
% è variabile in base all'anno corrente.
for i=1:nanni+10
    anno=2011+i;
    tab_anni(i+1,1)=anno;
    if tab_anni(i,3)==0
        tab_anni(i+1,2)=tab_anni(i,2)+31536000;
    else
        tab_anni(i+1,2)=tab_anni(i,2)+31622400;
    end
    if mod(anno,4)==0 && (mod(anno,100)~=0 || mod(anno,400)==0)  
        tab_anni(i+1,3)=1;
    else
        tab_anni(i+1,3)=0;
    end
end
% Di tutta la tabella interessa solo la riga dell'anno ottenendo i secondi
% progressivi trascorsi e l'informazione dell'anno bisestile  
for i=1:nanni+10
    if a==tab_anni(i,1)
        annocorrente=tab_anni(i,2);
        bisestile=tab_anni(i,3);
    end
end

% Identificazione del mese ================================================
% Per il mese si vuole creare una tabella simile a quella per l'anno ma in
% questo caso è più semplice in quanto la tabella dei mesi ha delle
% dimensioni costanti (valida per qualsiasi anno). Ci sono tre colonne: 
% la prima che indetifica il mese, la seconda e la terza identificano
% i secondi progressivi per ogni mese per l'anno non bisestile e bisestile.

%      mese                non bisestile (0)           bisestile (1)
tabella_mesi(1,1)=1;   tabella_mesi(1,2)=0;         tabella_mesi(1,3)=0;
tabella_mesi(2,1)=2;   tabella_mesi(2,2)=2678400;   tabella_mesi(2,3)=2678400;
tabella_mesi(3,1)=3;   tabella_mesi(3,2)=5097600;   tabella_mesi(3,3)=5184000;
tabella_mesi(4,1)=4;   tabella_mesi(4,2)=7776000;   tabella_mesi(4,3)=7862400;
tabella_mesi(5,1)=5;   tabella_mesi(5,2)=10368000;  tabella_mesi(5,3)=10454400;
tabella_mesi(6,1)=6;   tabella_mesi(6,2)=13046400;  tabella_mesi(6,3)=13132800;
tabella_mesi(7,1)=7;   tabella_mesi(7,2)=15638400;  tabella_mesi(7,3)=15724800;
tabella_mesi(8,1)=8;   tabella_mesi(8,2)=18316800;  tabella_mesi(8,3)=18403200;
tabella_mesi(9,1)=9;   tabella_mesi(9,2)=20995200;  tabella_mesi(9,3)=21081600;
tabella_mesi(10,1)=10; tabella_mesi(10,2)=23587200; tabella_mesi(10,3)=23673600;
tabella_mesi(11,1)=11; tabella_mesi(11,2)=26265600; tabella_mesi(11,3)=26352000;
tabella_mesi(12,1)=12; tabella_mesi(12,2)=28857600; tabella_mesi(12,3)=28944000;

% Identificazione del giorno ==============================================
% Come per la matrice dei mesi anche per i giorni si può creare una matrice
% in cui ci siamo 31 righe pari al massimo dei giorni possibili in un mese
% e i secondi progressivi. Per i mesi di Marzo e Ottobre abbiamo sempre il
% problema dell'ora legale. In questo caso non possiamo inglobarlo in
% questa tabella in quanto non per tuti i mesi si deve aggiugere o togliere
% un'ora.
% IMPORTANTE ==============================================================
% Con queste tabelle si vuole trasformare la codifica labview in data e ora
% che però sono da considerarsi in tempo assoluto, infatti non stiamo
% considerando il fuso orario Italiano e l'effetto dell'ora legale........
tabella_gg(1,1)=1; tabella_gg(1,2)=24*3600;
for i=2:31
    tabella_gg(i,1)=i; tabella_gg(i,2)=tabella_gg(i-1,2)+(24*3600);
end
% A questo punto ho tutti gli strumenti per trasformare il tempo labview in
% gg/mm/aaaa oo:mm:ss

% GESTIONE DEI FILE =======================================================
% L'obiettivo è quello di creare delle matrici o tabelle, una per ogni
% tipologia di file, in cui compaia la data e l'ora di riferimento del
% generico file. 
% L'analisi deve essere fatta con un ciclo for per scorrere tutti i file 
% all'interno della cartella di arrivo. Successivamente a seconda del 
% tipo di file letto entriamo in uno switch che identifica e gestisce i
% file a seconda della loro tipologia.....

% Identificazione dell'indice per le matrici separate di ogni file
ind_ma=1; ind_mo=1; ind_aneidro=1; ind_check=1; ind_sonar=1;
% Posizioniamoci nella cartella arrivo
cd(arrivo);
% ciclo for per la compilazione di 5 matrici con all'interno le medesime
% caratteristiche: codifica del file, gg, mm, aaaa, ore, minuti, secondi, 
% per il file check non esiste ora, minuti e secondi....
% definite globali per poterle visualizzare nel workspace di MATLAB
global mat_aneidro; global mat_sonar;

for i=3:n+2
    pezzofile=aa(i).name(1:2);
    switch pezzofile
        % Telecamera MANTOVA ==============================================
        case 'ma'
            mat_telma(ind_ma,1)=str2num(aa(i).name(8:end-4)); % nome file
            mat_telma(ind_ma,2)=str2num(aa(i).name(12:13));   % giorno
            mat_telma(ind_ma,3)=str2num(aa(i).name(10:11));   % mese
            mat_telma(ind_ma,4)=2000+str2num(aa(i).name(8:9));% anno
            mat_telma(ind_ma,5)=str2num(aa(i).name(14:15));   % ore
            mat_telma(ind_ma,6)=str2num(aa(i).name(16:17));   % minuti
            mat_telma(ind_ma,7)=str2num(aa(i).name(18:19));   % secondi
            ind_ma=ind_ma+1;
        % Telecamera MODENA ===============================================
        case 'mo'
            mat_telmo(ind_mo,1)=str2num(aa(i).name(7:end-4)); % nome file
            mat_telmo(ind_mo,2)=str2num(aa(i).name(11:12));   % giorno
            mat_telmo(ind_mo,3)=str2num(aa(i).name(9:10));    % mese
            mat_telmo(ind_mo,4)=2000+str2num(aa(i).name(7:8));     % anno
            mat_telmo(ind_mo,5)=str2num(aa(i).name(13:14));   % ore
            mat_telmo(ind_mo,6)=str2num(aa(i).name(15:16));   % minuti
            mat_telmo(ind_mo,7)=str2num(aa(i).name(17:18));   % secondi
            ind_mo=ind_mo+1;
        % Check router ====================================================
        case 'ro'
            % lettura della parte alfanumerica es. (9-5-12) (m-g-aa)
            codifica=aa(i).name(7:end-4);
            % identifico la posizione di '-' in quanto non hanno posizione
            % fissa dato che sia il mese che il giorno possono composti da 
            % 2 numeri.
            trova=findstr(codifica,'-');
            % identifico la stringa mese
            ch_mese=codifica(1:trova(1)-1);
            % identifico la stringa giorno
            ch_giorno=codifica(trova(1)+1:trova(2)-1);
            % identifico la stringa anno
            ch_anno=codifica(trova(2)+1:end);
            % unisco le stringhe per formare la data 
            ch_data=strcat(ch_giorno,ch_mese,ch_anno);
            % creao tabella check
            mat_check(ind_check,1)=str2num(ch_data); % nome file (+/-)
            mat_check(ind_check,2)=str2num(ch_giorno); % giorno
            mat_check(ind_check,3)=str2num(ch_mese); % mese
            mat_check(ind_check,4)=str2num(ch_anno)+2000; % anno
            ind_check=ind_check+1;
        % Anemometro e Idrometro ==========================================
        case 'an'
            mat_aneidro(ind_aneidro,1)=str2num(aa(i).name(7:end-4)); % nome file
            % procedimento per la detemrinazione dell'anno del file
            for i=1:nanni+9
                if mat_aneidro(ind_aneidro,1) >= tab_anni(i,2) && mat_aneidro(ind_aneidro,1) < tab_anni(i+1,2)
                    mat_aneidro(ind_aneidro,4)=tab_anni(i,1);
                    det_mese(ind_aneidro,1)=mat_aneidro(ind_aneidro,1)-tab_anni(i,2);
                    det_mese(ind_aneidro,2)=tab_anni(i,3);
                end
            end
            ind_aneidro=ind_aneidro+1;
            % determinazione del mese del file
            for i=1:size(det_mese,1)
                for j=1:12-1 % per tabella_mesi
                    switch det_mese(i,2)
                        case 0 % non bisetile, colonna 2 di tabella_mesi
                            if det_mese(i,1) >= tabella_mesi(j,2) && det_mese(i,1) < tabella_mesi(j+1,2)
                                mat_aneidro(i,3)=tabella_mesi(j,1);
                                det_giorno(i,1)=det_mese(i,1)-tabella_mesi(j,2);
                            end
                        case 1 % non bisetile, colonna 3 di tabella_mesi
                            if det_mese(i,1) >= tabella_mesi(j,3) && det_mese(i,1) < tabella_mesi(j+1,3)
                                mat_aneidro(i,3)=tabella_mesi(j,1);
                                det_giorno(i,1)=det_mese(i,1)-tabella_mesi(j,3);
                            end
                    end
                end
            end
            % determinazione del giorno del file
            for i=1:size(det_giorno,1)
                for j=1:31-1 % per tabella_mese
                    if det_giorno(i,1)<86400
                        mat_aneidro(i,2)=1;
                        det_ora(i,1)=det_giorno(i,1);
                    end
                    if det_giorno(i,1) >= tabella_gg(j,2) && det_giorno(i,1) < tabella_gg(j+1,2)
                        mat_aneidro(i,2)=tabella_gg(j+1,1);
                        det_ora(i,1)=det_giorno(i,1)-tabella_gg(j,2);
                    end
                end
            end
            % determinazione dell'ora e dei minuti (l'orario a questo punto
            % non è universale in quanto è affetto dall'ora legale)
            for i=1:size(det_ora,1)
                % determinazione dell'ora
                mat_aneidro(i,5)=fix(det_ora(i,1)/3600);
                minuti=mod(det_ora(i,1),3600);
                % determinazione dei minuti
                mat_aneidro(i,6)=fix(minuti/60);
                % determinazione dei secondi
                mat_aneidro(i,7)=mod(minuti,60);
            end
        % Sonar ===========================================================
        case 'so'
            mat_sonar(ind_sonar,1)=str2num(aa(i).name(6:end-4)); % nome file
            % procedimento per la detemrinazione dell'anno del file
            for i=1:nanni+9
                if mat_sonar(ind_sonar,1) >= tab_anni(i,2) && mat_sonar(ind_sonar,1) < tab_anni(i+1,2)
                    mat_sonar(ind_sonar,4)=tab_anni(i,1);
                    det_meses(ind_sonar,1)=mat_sonar(ind_sonar,1)-tab_anni(i,2);
                    det_meses(ind_sonar,2)=tab_anni(i,3);
                end
            end
            ind_sonar=ind_sonar+1;
            % determinazione del mese del file
            for i=1:size(det_meses,1)
                for j=1:12-1 % per tabella_mesi
                    switch det_meses(i,2)
                        case 0 % non bisetile, colonna 2 di tabella_mesi
                            if det_meses(i,1) >= tabella_mesi(j,2) && det_meses(i,1) < tabella_mesi(j+1,2)
                                mat_sonar(i,3)=tabella_mesi(j,1);
                                det_giornos(i,1)=det_meses(i,1)-tabella_mesi(j,2);
                            end
                        case 1 % bisetile, colonna 3 di tabella_mesi
                            if det_meses(i,1) >= tabella_mesi(j,3) && det_meses(i,1) < tabella_mesi(j+1,3)
                                mat_sonar(i,3)=tabella_mesi(j,1);
                                det_giornos(i,1)=det_meses(i,1)-tabella_mesi(j,3);
                            end
                    end
                end
            end
            % determinazione del giorno del file
            for i=1:size(det_giornos,1)
                for j=1:31-1 % per tabella_mese
                    if det_giornos(i,1)<86400
                        mat_sonar(i,2)=1;
                        det_oras(i,1)=det_giornos(i,1);
                    end
                    if det_giornos(i,1) >= tabella_gg(j,2) && det_giornos(i,1) < tabella_gg(j+1,2)
                        mat_sonar(i,2)=tabella_gg(j+1,1);
                        det_oras(i,1)=det_giornos(i,1)-tabella_gg(j,2);
                    end
                end
            end
            % determinazione dell'ora e dei minuti (l'orario a questo punto
            % non è universale in quanto è affetto dall'ora legale)
            for i=1:size(det_oras,1)
                % determinazione dell'ora
                mat_sonar(i,5)=fix(det_oras(i,1)/3600);
                minutis=mod(det_oras(i,1),3600);
                % determinazione dei minuti
                mat_sonar(i,6)=fix(minutis/60);
                % determinazione dei secondi
                mat_sonar(i,7)=mod(minutis,60);
            end
    end
end
% Nelle tabelle appena compilate compaiono i nomi dei file in prima colonna
% e nelle successive le informazioni temporali: giorno, mese, anno, ora,
% minuti e secondi (tranne che per il file di check).
% i nomi di queste tabelle sono:
% mat_aneidro // mat_sonar // mat_check // mat_telma // mat_telmo
% ATTENZIONE: le matrici che si possono visualizzare a fine programma non
% sono quelle descritte fino ad ora ma comprendono ulteriori colonne
% aggiunte in seguito.....

%=================== FUSO ORARIO E ORA LEGALE/SOLARE ======================
% FILE GIA' SISTEMATI =====================================================
% Le matrici delle telecamere sono già in orario italiano e comprendono già
% il cambio d'ora da solare a legale grazie all'impostazione delle
% telecamere. Non devono essere effettuate ulteriori modifiche
% FILE ININFLUENTI ========================================================
% Il file check è un file giornaliero che semplicemente segnala se ci sono
% problemi con la connessione del router...
% FILE DA SISTEMARE =======================================================
% I file da sistemare sono quelli del SONAR, ANE_IDRO. Nella tabella creata
% il tempo è assoluto. Due operazioni:
% FUSO ORARIO: semplicemente si deve aggiungere un'ora ( con la
% complicazione del cambio di giorno mese o anno)
% ORA LEGALE: aggiungere o togliere un'altra ora solo in periodi ben
% definiti ma variabili anno per anno....
%==========================================================================
%==========================================================================
% Creazione tabella giorni in un mese bisestile e non

%      mese                non bisestile (0)          bisestile (1)
ngiorni_mese(1,1)=1;     ngiorni_mese(1,2)=31;     ngiorni_mese(1,3)=31;
ngiorni_mese(2,1)=2;     ngiorni_mese(2,2)=28;     ngiorni_mese(2,3)=29;
ngiorni_mese(3,1)=3;     ngiorni_mese(3,2)=31;     ngiorni_mese(3,3)=31;
ngiorni_mese(4,1)=4;     ngiorni_mese(4,2)=30;     ngiorni_mese(4,3)=30;
ngiorni_mese(5,1)=5;     ngiorni_mese(5,2)=31;     ngiorni_mese(5,3)=31;
ngiorni_mese(6,1)=6;     ngiorni_mese(6,2)=30;     ngiorni_mese(6,3)=30;
ngiorni_mese(7,1)=7;     ngiorni_mese(7,2)=31;     ngiorni_mese(7,3)=31;
ngiorni_mese(8,1)=8;     ngiorni_mese(8,2)=31;     ngiorni_mese(8,3)=31;
ngiorni_mese(9,1)=9;     ngiorni_mese(9,2)=30;     ngiorni_mese(9,3)=30;
ngiorni_mese(10,1)=10;   ngiorni_mese(10,2)=31;    ngiorni_mese(10,3)=31;
ngiorni_mese(11,1)=11;   ngiorni_mese(11,2)=30;    ngiorni_mese(11,3)=30;
ngiorni_mese(12,1)=12;   ngiorni_mese(12,2)=31;    ngiorni_mese(12,3)=31;
% per le matrici sonar e aneidro si devono aggiungere delle informazioni
% utili per la trasformazione del tempo da assoluto a "Italiano".
% Aggiunta della colonna per il bisestile (sempre per avere uno programma
% dinamico, anche se poi sarà limitato nel suo utilizzo)

% aggiunta colonna "8" per informazione bisestile =========================
% ane_idro
for i=1:size(mat_aneidro,1)
    aaa=mat_aneidro(i,4);
    if mod(aaa,4)==0 && (mod(aaa,100)~=0 || mod(aaa,400)==0)  
        mat_aneidro(i,8)=1;
    else
        mat_aneidro(i,8)=0;
    end
end
% stessa cosa per sonar
for i=1:size(mat_sonar,1)
    aaa=mat_sonar(i,4);
    if mod(aaa,4)==0 && (mod(aaa,100)~=0 || mod(aaa,400)==0)  
        mat_sonar(i,8)=1;
    else
        mat_sonar(i,8)=0;
    end
end
%==========================================================================
% aggiunta della colonna "9" con il numero dei gg nel mese di riferimento
% considerando anche l'anno bisestile.....
for i=1:size(mat_aneidro,1)
    for j=1:12
        if mat_aneidro(i,3)==ngiorni_mese(j,1)
            if mat_aneidro(i,8)==0;
                mat_aneidro(i,9)=ngiorni_mese(j,2);
            else
                mat_aneidro(i,9)=ngiorni_mese(j,3);
            end
        end
    end
end
% stessa cosa per sonar
for i=1:size(mat_sonar,1)
    for j=1:12
        if mat_sonar(i,3)==ngiorni_mese(j,1)
            if mat_sonar(i,8)==0;
                mat_sonar(i,9)=ngiorni_mese(j,2);
            else
                mat_sonar(i,9)=ngiorni_mese(j,3);
            end
        end
    end
end

%==========================================================================
% TABELLE CONVERSIONE LABVIEW IN DATA ASSOLUTA ============================
% per separare le operaioni legate all'orario e tenere traccia dei passaggi 
% le tabelle non verranno sovrascritte ma cambieranno di nome......
% Elenco tabelle con semplce trasformazione temporale 
% mat_sonar //  mat_aneidro (quelle fatte adesso)
%==========================================================================
% OPERAZIONE FUSO ORARIO
% - sommare un'ora a quella in tabella. Se l'ora in tabella è 23 devo 
%   implementare il cambio di giorno, giorno/mese e giorno/mese/anno....  
global mat_aneidro_fuso; global mat_sonar_fuso;

% ANEMOMETRO e IDROMETRO ==================================================
for i=1:size(mat_aneidro,1)
    % la 1a,6a e 7a colonna è invariata a prescindere dal fuso orario
    mat_aneidro_fuso(i,1)=mat_aneidro(i,1);
    mat_aneidro_fuso(i,6)=mat_aneidro(i,6);
    mat_aneidro_fuso(i,7)=mat_aneidro(i,7);
    mat_aneidro_fuso(i,8)=mat_aneidro(i,8);
    mat_aneidro_fuso(i,9)=mat_aneidro(i,9);
    % casi particolari:
    % se l'orario è 23 devo mettere 0
    if mat_aneidro(i,5)==23 
        mat_aneidro_fuso(i,5)=0;
        % se è l'ultimo giorno del mese devo anche ricominciare da 1 
        if mat_aneidro(i,2)==mat_aneidro(i,9)
            mat_aneidro_fuso(i,2)=1;
            % se è l'ultimo mese devo ricominciare da 1 e cambiare anno
            if mat_aneidro(i,3)==12
                mat_aneidro_fuso(i,3)=1;
                mat_aneidro_fuso(i,4)=mar_aneidro(i,4)+1;
            else
                mat_aneidro_fuso(i,3)=mat_aneidro(i,3)+1;
                mat_aneidro_fuso(i,4)=mat_aneidro(i,4);
            end
        % altrimenti sommo un giorno
        else
            mat_aneidro_fuso(i,2)=mat_aneidro(i,2)+1;
            mat_aneidro_fuso(i,3)=mat_aneidro(i,3);
            mat_aneidro_fuso(i,4)=mat_aneidro(i,4);
        end
    % se l'ora non è 23 sommo uno e il resto è invariato....    
    else
        mat_aneidro_fuso(i,2)=mat_aneidro(i,2);
        mat_aneidro_fuso(i,3)=mat_aneidro(i,3);
        mat_aneidro_fuso(i,4)=mat_aneidro(i,4);
        mat_aneidro_fuso(i,5)=mat_aneidro(i,5)+1; 
    end
end
% SONAR ===================================================================
for i=1:size(mat_sonar,1)
    % la 1a,6a e 7a colonna è invariata a prescindere dal fuso orario
    mat_sonar_fuso(i,1)=mat_sonar(i,1);
    mat_sonar_fuso(i,6)=mat_sonar(i,6);
    mat_sonar_fuso(i,7)=mat_sonar(i,7);
    mat_sonar_fuso(i,8)=mat_sonar(i,8);
    mat_sonar_fuso(i,9)=mat_sonar(i,9);
    % casi particolari:
    % se l'orario è 23 devo mettere 0
    if mat_sonar(i,5)==23 
        mat_sonar_fuso(i,5)=0;
        % se è l'ultimo giorno del mese devo anche ricominciare da 1 
        if mat_sonar(i,2)==mat_sonar(i,9)
            mat_sonar_fuso(i,2)=1;
            % se è l'ultimo mese devo ricominciare da 1 e cambiare anno
            if mat_aneidro(i,3)==12
                mat_sonar_fuso(i,3)=1;
                mat_sonar_fuso(i,4)=mar_sonar(i,4)+1;
            else
                mat_sonar_fuso(i,3)=mat_sonar(i,3)+1;
                mat_sonar_fuso(i,4)=mat_sonar(i,4);
            end
        % altrimenti sommo un giorno
        else
            mat_sonar_fuso(i,2)=mat_sonar(i,2)+1;
            mat_sonar_fuso(i,3)=mat_sonar(i,3);
            mat_sonar_fuso(i,4)=mat_sonar(i,4);
        end
    % se l'ora non è 23 sommo uno e il resto è invariato....    
    else
        mat_sonar_fuso(i,2)=mat_sonar(i,2);
        mat_sonar_fuso(i,3)=mat_sonar(i,3);
        mat_sonar_fuso(i,4)=mat_sonar(i,4);
        mat_sonar_fuso(i,5)=mat_sonar(i,5)+1; 
    end
end

%==========================================================================
% TABELLE CONVERSIONE DATA ASSOLUTA DATA ITALIANA (FUSO ORARIO)============
% per separare le operaioni legate all'orario e tenere traccia dei passaggi 
% le tabelle non verranno sovrascritte ma cambieranno di nome......
% Elenco tabelle con semplce trasformazione temporale 
% mat_sonar_fuso //  mat_aneidro_fuso
% in seguito potrebbero aggiungersi colonne per ulteriori informazioni....
%==========================================================================

%==========================================================================
%==========================================================================
% OPERAZIONE ORA LEGALE
% Si deve aggiungere o togliere un'ora rispettivamente nei mesi di Marzo
% e Ottobre. La giornata in cui c'è il cambio dell'ora è sempre la stessa
% ma non è una data fissa bensì l'ultima domenica dei due mesi che però,
% anno dopo anno, cade in giorni diversi.....

%==========================================================================
% Determinazione del giorno della settimana con cui comincia il mese di
% Marzo per l'anno immesso dall'utente. 
if mod(a,4)==0 && (mod(a,100)~=0 || mod(a,400)==0)  
    primo_gg_marzo=tabellamesitraslata(3,3);
else
    primo_gg_marzo=tabellamesitraslata(3,2);
end
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch primo_gg_marzo
    case 0
        primo_gg_marzo_str='Dom'; % 0=domenica
    case 1
        primo_gg_marzo_str='Lun'; % 1=lunedì
    case 2
        primo_gg_marzo_str='Mar'; % 2=martedì
    case 3
        primo_gg_marzo_str='Mer'; % 3=mercoledì
    case 4
        primo_gg_marzo_str='Gio'; % 4=giovedì
    case 5
        primo_gg_marzo_str='Ven'; % 5=venerdì
    case 6
        primo_gg_marzo_str='Sab'; % 6=sabato
end
%==========================================================================
% Determinazione del giorno della settimana con cui comincia il mese di
% Ottobre per l'anno immesso dall'utente. 
if mod(a,4)==0 && (mod(a,100)~=0 || mod(a,400)==0)  
    primo_gg_ottobre=tabellamesitraslata(10,3);
else
    primo_gg_ottobre=tabellamesitraslata(10,2);
end
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch primo_gg_ottobre
    case 0
        primo_gg_ottobre_str='Dom'; % 0=domenica
    case 1
        primo_gg_ottobre_str='Lun'; % 1=lunedì
    case 2
        primo_gg_ottobre_str='Mar'; % 2=martedì
    case 3
        primo_gg_ottobre_str='Mer'; % 3=mercoledì
    case 4
        primo_gg_ottobre_str='Gio'; % 4=giovedì
    case 5
        primo_gg_ottobre_str='Ven'; % 5=venerdì
    case 6
        primo_gg_ottobre_str='Sab'; % 6=sabato
end

%==========================================================================
% Creazione di una tabella in cui si inseriscano per i due mesi dell'anno
% inserito dall'utente, i giorni della settimana....
% esempio della tabella (la prima riga è determinata dalle operazioni
% precedenti di determinazione del primo giorno del mese....

%  giorno       Marzo     Ottobre        
%    1            4          1         
%    2            5          2        
%    3            6          3           
%    4            0          4           
%  ....         ....        ....         
%   31          ....        ....       

% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab

% compilazione della prima riga della matrice
tab_mar_ott(1,1)=1;
tab_mar_ott(1,2)=primo_gg_marzo;
tab_mar_ott(1,3)=primo_gg_ottobre;
% compilazione del resto della matrice
for i=2:31
    tab_mar_ott(i,1)=tab_mar_ott(i-1,1)+1;
    % if per marzo
    if tab_mar_ott(i-1,2)==6
        tab_mar_ott(i,2)=0;
    else
        tab_mar_ott(i,2)=tab_mar_ott(i-1,2)+1;
    end
    % if per ottobre
    if tab_mar_ott(i-1,3)==6
        tab_mar_ott(i,3)=0;
    else
        tab_mar_ott(i,3)=tab_mar_ott(i-1,3)+1;
    end
end
% adesso devo identificare il giorno del mese di marzo e ottobre in cui
% cade l'ultima domenica del mese....
for i=25:31
    if tab_mar_ott(i,2)==0;
        ult_dom_mar=tab_mar_ott(i,1);
    end
    if tab_mar_ott(i,3)==0;
        ult_dom_ott=tab_mar_ott(i,1);
    end
end
%==========================================================================

%==========================================================================
% ORA LEGALE ==============================================================
% Ultima domenica di marzo  : 02:00 --> 03:00 (perdo un'ora) ==============
% Ultima domenica di ottobre: 03:00 --> 02:00 (guadagno un'ora) ===========
%==========================================================================

% considerando un anno di dati ogni riga può rientrare in 3 categorie
% "gen-mar" e "ott-dic" ora solare (no cambio data)
% "mar-ott" ora legale (più un ora)
% i mesi di marzo e ottobre sono particolari in quanto si possono 
% suddividere in prima del cambio ora e dopo cambio ora; anche 
% se l'orario è fissato la data cambia anno per anno... 

% aggiungere una colonna "8" alle matrici mat_aneidro_fuso e mat_sonar_fuso
% in cui si codifica la zona temporale in cui si trova il file
% 0 periodo dell'ora solare (non modifico l'ora)
% 1 periodo ora legale (sommo un'ora)
% 2 ora esatta di ottobre in cui si toglie un ora (elimino il file dall'elenco)

for i=1:size(mat_aneidro_fuso,1)
    % analizzo la colonna dei mesi
    switch mat_aneidro_fuso(i,3)
        %==================================================================
        case {1, 2, 11, 12} % gen, feb, nov, dic ora solare "0"
            mat_aneidro_fuso(i,10)=0;
        %==================================================================
        case {4, 5, 6, 7, 8, 9} % da apr a set ora legale "1"
            mat_aneidro_fuso(i,10)=1;
        %==================================================================
        case 3 % mese = mese di marzo, dipende dal giorno
            % giorno < ultima domenica di marzo, ora solare "0"
            if mat_aneidro_fuso(i,2) < ult_dom_mar
                mat_aneidro_fuso(i,10)=0;
            end
            % giorno > ultima domenica di marzo ora legale "1"
            if mat_aneidro_fuso(i,2) > ult_dom_mar
                mat_aneidro_fuso(i,10)=1;
            end
            % giorno = ultima domenica di marzo, dipende dall'ora
            if mat_aneidro_fuso(i,2) == ult_dom_mar
                % ora < 02:00 ora solare "0"
                if mat_aneidro_fuso(i,5) < 2
                    mat_aneidro_fuso(i,10)=0;
                % ora >= 02:00 ora legale "1"
                else
                    mat_aneidro_fuso(i,10)=1;
                end       
            end
        %==================================================================    
        case 10 % mese = mese di ottobre, dipende dal giorno
            % giorno < ultima domenica di ottobre, ora legale "1"
            if mat_aneidro_fuso(i,2) < ult_dom_ott
                mat_aneidro_fuso(i,10)=1;
            end
            % giorno > ultima domenica di ottobre ora solare "0"
            if mat_aneidro_fuso(i,2) > ult_dom_ott
                mat_aneidro_fuso(i,10)=0;
            end
            % giorno = ultima domenica di ottobre, dipende dall'ora
            if mat_aneidro_fuso(i,2) == ult_dom_ott
                % ora < 03:00 ora legale "1"
                if mat_aneidro_fuso(i,5) < 3
                    mat_aneidro_fuso(i,10)=1;
                end
                % ora > 03:00 ora solare "0"
                if mat_aneidro_fuso(i,5) > 3
                    mat_aneidro_fuso(i,10)=0;
                end
                % ora == 03:00 
                if mat_aneidro_fuso(i,5) == 3 && mat_aneidro_fuso(i,6) == 0 && mat_aneidro_fuso(i,7) == 0
                    mat_aneidro_fuso(i,10)=2;
                end       
            end
    end
end


% stessa cosa per il sonar
for i=1:size(mat_sonar_fuso,1)
    % analizzo la colonna dei mesi
    switch mat_sonar_fuso(i,3)
        %==================================================================
        case {1, 2, 11, 12} % gen, feb, nov, dic ora solare "0"
            mat_sonar_fuso(i,10)=0;
        %==================================================================
        case {4, 5, 6, 7, 8, 9} % da apr a set ora legale "1"
            mat_sonar_fuso(i,10)=1;
        %==================================================================
        case 3 % mese = mese di marzo, dipende dal giorno
            % giorno < ultima domenica di marzo, ora solare "0"
            if mat_sonar_fuso(i,2) < ult_dom_mar
                mat_sonar_fuso(i,10)=0;
            end
            % giorno > ultima domenica di marzo ora legale "1"
            if mat_sonar_fuso(i,2) > ult_dom_mar
                mat_sonar_fuso(i,10)=1;
            end
            % giorno = ultima domenica di marzo, dipende dall'ora
            if mat_sonar_fuso(i,2) == ult_dom_mar
                % ora < 02:00 ora solare "0"
                if mat_sonar_fuso(i,5) < 2
                    mat_sonar_fuso(i,10)=0;
                % ora >= 02:00 ora legale "1"
                else
                    mat_sonar_fuso(i,10)=1;
                end       
            end
        %==================================================================    
        case 10 % mese = mese di ottobre, dipende dal giorno
            % giorno < ultima domenica di ottobre, ora legale "1"
            if mat_sonar_fuso(i,2) < ult_dom_ott
                mat_sonar_fuso(i,10)=1;
            end
            % giorno > ultima domenica di ottobre ora solare "0"
            if mat_sonar_fuso(i,2) > ult_dom_ott
                mat_sonar_fuso(i,10)=0;
            end
            % giorno = ultima domenica di ottobre, dipende dall'ora
            if mat_sonar_fuso(i,2) == ult_dom_ott
                % ora < 03:00 ora legale "1"
                if mat_sonar_fuso(i,5) < 3
                    mat_sonar_fuso(i,10)=1;
                end
                % ora > 03:00 ora solare "0"
                if mat_sonar_fuso(i,5) > 3
                    mat_sonar_fuso(i,10)=0;
                end
                % ora == 03:00 
                if mat_sonar_fuso(i,5) == 3 && mat_sonar_fuso(i,6) == 0 && mat_sonar_fuso(i,7) == 0
                    mat_sonar_fuso(i,10)=2;
                end       
            end
    end
end

%==========================================================================
% generazione della matrice che tiene conto anche dell'ora legale. il
% procedimento è identico all'introduzione del fuso orario soltanto che in
% questo caso si deve sommare un'ora solo se si rientra nel periodo
% dell'ora legale, ovverosia quando in colonna 10 c'è "1".
% In caso di valore pari a "2" la riga viene eliminata!
global mat_aneidro_fuso_legale; global mat_sonar_fuso_legale;

for i=1:size(mat_aneidro_fuso,1)
    % la 1a,6a e 7a colonna è invariata a prescindere dal fuso orario
    mat_aneidro_fuso_legale(i,1)=mat_aneidro_fuso(i,1);
    mat_aneidro_fuso_legale(i,6)=mat_aneidro_fuso(i,6);
    mat_aneidro_fuso_legale(i,7)=mat_aneidro_fuso(i,7);
    mat_aneidro_fuso_legale(i,8)=mat_aneidro_fuso(i,10);
    % l'aggiunta dell'ora legale dipende dalla colonna 10
    switch mat_aneidro_fuso(i,10)
        case 1 % devo aggiungere un'ora
            % casi particolari:
            % se l'orario è 23 devo mettere 0
            if mat_aneidro_fuso(i,5)==23 
                mat_aneidro_fuso_legale(i,5)=0;
                % se è l'ultimo giorno del mese devo anche ricominciare da 1 
                if mat_aneidro_fuso(i,2)==mat_aneidro_fuso(i,9)
                    mat_aneidro_fuso_legale(i,2)=1;
                    % se è l'ultimo mese devo ricominciare da 1 e cambiare anno
                    if mat_aneidro_fuso(i,3)==12
                        mat_aneidro_fuso_legale(i,3)=1;
                        mat_aneidro_fuso_legale(i,4)=mar_aneidro_fuso(i,4)+1;
                    else
                        mat_aneidro_fuso_legale(i,3)=mat_aneidro_fuso(i,3)+1;
                        mat_aneidro_fuso_legale(i,4)=mat_aneidro_fuso(i,4);
                    end
                % altrimenti sommo un giorno
                else
                    mat_aneidro_fuso_legale(i,2)=mat_aneidro_fuso(i,2)+1;
                    mat_aneidro_fuso_legale(i,3)=mat_aneidro_fuso(i,3);
                    mat_aneidro_fuso_legale(i,4)=mat_aneidro_fuso(i,4);
                end
            % se l'ora non è 23 sommo uno e il resto è invariato....    
            else
                mat_aneidro_fuso_legale(i,2)=mat_aneidro_fuso(i,2);
                mat_aneidro_fuso_legale(i,3)=mat_aneidro_fuso(i,3);
                mat_aneidro_fuso_legale(i,4)=mat_aneidro_fuso(i,4);
                mat_aneidro_fuso_legale(i,5)=mat_aneidro_fuso(i,5)+1; 
            end
        otherwise
            mat_aneidro_fuso_legale(i,2)=mat_aneidro_fuso(i,2);
            mat_aneidro_fuso_legale(i,3)=mat_aneidro_fuso(i,3);
            mat_aneidro_fuso_legale(i,4)=mat_aneidro_fuso(i,4);
            mat_aneidro_fuso_legale(i,5)=mat_aneidro_fuso(i,5);
    end 
end

%==========================================================================
%==========================================================================
% devo eliminare l'eventuale riga con la codifica 2
% identifico l'indice della riga
riga_eliminare_aneidro=0;
for i=1:size(mat_aneidro_fuso_legale,1)
    if mat_aneidro_fuso_legale(i,8)==2
        riga_eliminare_aneidro=i;
    end
end
% eliminazione della riga
if riga_eliminare_aneidro > 0
    provvisorio=mat_aneidro_fuso_legale;
    provvisorio(riga_eliminare_aneidro,:)=[];
    mat_aneidro_definitiva=provvisorio;
    clear provvisorio;
    mat_aneidro_definitiva(:,8)=[];
else
    mat_aneidro_definitiva=mat_aneidro_fuso_legale;
    mat_aneidro_definitiva(:,8)=[];
end
%==========================================================================
%==========================================================================
% Inoltre avrò sempre, ipotizzando di avere tutti i file ogni ora due file
% consecutivi a livello temporale assoluto che avranno il stesso tempo ora
% italiana. Esempio:
% il file datato 02:40 --> 03:40 a causa del'ora legale
% il file datato 03:40 --> 03:40 terminata l'ora legale
% uno dei due deve essere eliminato.....

% identifico la riga doppia
 
for i=1:size(mat_aneidro_fuso_legale,1)-1
    if mat_aneidro_fuso_legale(i,5) == mat_aneidro_fuso_legale(i+1,5)
        if mat_aneidro_fuso_legale(i,6) == mat_aneidro_fuso_legale(i+1,6)
            if mat_aneidro_fuso_legale(i,7) == mat_aneidro_fuso_legale(i+1,7)
                rigadaeliminare=i+1;
            end
        end
    end
end

mat_aneidro_definitiva(rigadaeliminare,:)=[];

% anche per il sonar
for i=1:size(mat_sonar_fuso,1)
    % la 1a,6a e 7a colonna è invariata a prescindere dal fuso orario
    mat_sonar_fuso_legale(i,1)=mat_sonar_fuso(i,1);
    mat_sonar_fuso_legale(i,6)=mat_sonar_fuso(i,6);
    mat_sonar_fuso_legale(i,7)=mat_sonar_fuso(i,7);
    mat_sonar_fuso_legale(i,8)=mat_sonar_fuso(i,10);
    % l'aggiunta dell'ora legale dipende dalla colonna 10
    switch mat_sonar_fuso(i,10)
        case 1 % devo aggiungere un'ora
            % casi particolari:
            % se l'orario è 23 devo mettere 0
            if mat_sonar_fuso(i,5)==23 
                mat_sonar_fuso_legale(i,5)=0;
                % se è l'ultimo giorno del mese devo anche ricominciare da 1 
                if mat_sonar_fuso(i,2)==mat_sonar_fuso(i,9)
                    mat_sonar_fuso_legale(i,2)=1;
                    % se è l'ultimo mese devo ricominciare da 1 e cambiare anno
                    if mat_sonar_fuso(i,3)==12
                        mat_sonar_fuso_legale(i,3)=1;
                        mat_sonar_fuso_legale(i,4)=mar_sonar_fuso(i,4)+1;
                    else
                        mat_sonar_fuso_legale(i,3)=mat_sonar_fuso(i,3)+1;
                        mat_sonar_fuso_legale(i,4)=mat_sonar_fuso(i,4);
                    end
                % altrimenti sommo un giorno
                else
                    mat_sonar_fuso_legale(i,2)=mat_sonar_fuso(i,2)+1;
                    mat_sonar_fuso_legale(i,3)=mat_sonar_fuso(i,3);
                    mat_sonar_fuso_legale(i,4)=mat_sonar_fuso(i,4);
                end
            % se l'ora non è 23 sommo uno e il resto è invariato....    
            else
                mat_sonar_fuso_legale(i,2)=mat_sonar_fuso(i,2);
                mat_sonar_fuso_legale(i,3)=mat_sonar_fuso(i,3);
                mat_sonar_fuso_legale(i,4)=mat_sonar_fuso(i,4);
                mat_sonar_fuso_legale(i,5)=mat_sonar_fuso(i,5)+1; 
            end
        otherwise
            mat_sonar_fuso_legale(i,2)=mat_sonar_fuso(i,2);
            mat_sonar_fuso_legale(i,3)=mat_sonar_fuso(i,3);
            mat_sonar_fuso_legale(i,4)=mat_sonar_fuso(i,4);
            mat_sonar_fuso_legale(i,5)=mat_sonar_fuso(i,5);
    end 
end
%==========================================================================
%==========================================================================
% devo eliminare l'eventuale riga con la codifica 2
% identifico l'indice della riga
riga_eliminare_sonar=0;
for i=1:size(mat_sonar_fuso_legale,1)
    if mat_sonar_fuso_legale(i,8)==2
        riga_eliminare_sonar=i;
    end
end
% eliminazione della riga
if riga_eliminare_sonar >0
    provvisorio=mat_sonar_fuso_legale;
    provvisorio(riga_eliminare_sonar,:)=[];
    mat_sonar_definitiva=provvisorio;
    clear provvisorio;
    mat_sonar_definitiva(:,8)=[];
else
    mat_sonar_definitiva=mat_sonar_fuso_legale;
    mat_sonar_definitiva(:,8)=[];
end
%==========================================================================
%==========================================================================
% Inoltre avrò sempre, ipotizzando di avere tutti i file ogni ora due file
% consecutivi a livello temporale assoluto che avranno il stesso tempo ora
% italiana. Esempio:
% il file datato 02:40 --> 03:40 a causa del'ora legale
% il file datato 03:40 --> 03:40 terminata l'ora legale
% uno dei due deve essere eliminato.....

% identifico la riga doppia
 
for i=1:size(mat_sonar_fuso_legale,1)-1
    if mat_sonar_fuso_legale(i,5) == mat_sonar_fuso_legale(i+1,5)
        if mat_sonar_fuso_legale(i,6) == mat_sonar_fuso_legale(i+1,6)
            if mat_sonar_fuso_legale(i,7) == mat_sonar_fuso_legale(i+1,7)
                rigadaeliminare=i+1;
            end
        end
    end
end

mat_sonar_definitiva(rigadaeliminare,:)=[];
%==========================================================================
%==========================================================================
% visualizzazione dei dati presenti nella cartella scelta...
global matrice_aneidro_database; global matrice_sonar_database;
global matrice_telma_database; global matrice_telmo_database;
% =========================================================================
% =========================================================================
% ========================== DATABASE ANEIDRO =============================
% =========================================================================
% =========================================================================
% Matrice Anemometro_Idrometro che identifica il numero di file regitrati
% nello stesso giorno. Dato che sono conteggiati anche i file zero se il
% numero eccede 24 impongo 24, tanto serve per una prima viasualizzazione!
matrice_aneidro_database(1,1)=mat_aneidro_definitiva(1,2);
matrice_aneidro_database(1,2)=mat_aneidro_definitiva(1,3);
matrice_aneidro_database(1,3)=mat_aneidro_definitiva(1,4);
matrice_aneidro_database(1,4)=1;
ind_dat=1;

for i=2:size(mat_aneidro_definitiva,1)
   if mat_aneidro_definitiva(i,4)==mat_aneidro_definitiva(i-1,4) % stesso anno
       if mat_aneidro_definitiva(i,3)==mat_aneidro_definitiva(i-1,3) % stesso mese
           if mat_aneidro_definitiva(i,2)==mat_aneidro_definitiva(i-1,2) % stesso giorno
               matrice_aneidro_database(ind_dat,4)=matrice_aneidro_database(ind_dat,4)+1;
               
           else % mantengo mese/anno e cambio giorno
               matrice_aneidro_database(ind_dat+1,1)=mat_aneidro_definitiva(i,2);
               matrice_aneidro_database(ind_dat+1,2)=mat_aneidro_definitiva(i-1,3);
               matrice_aneidro_database(ind_dat+1,3)=mat_aneidro_definitiva(i-1,4);
               matrice_aneidro_database(ind_dat+1,4)=1;
               ind_dat=ind_dat+1;
           end
       else % mantengo anno cambio mese e giorno
           matrice_aneidro_database(ind_dat+1,1)=mat_aneidro_definitiva(i,2);
           matrice_aneidro_database(ind_dat+1,2)=mat_aneidro_definitiva(i,3);
           matrice_aneidro_database(ind_dat+1,3)=mat_aneidro_definitiva(i-1,4);
           matrice_aneidro_database(ind_dat+1,4)=1;
           ind_dat=ind_dat+1;
       end
   else %cambio tutto
       matrice_aneidro_database(ind_dat+1,1)=mat_aneidro_definitiva(i,2);
       matrice_aneidro_database(ind_dat+1,2)=mat_aneidro_definitiva(i,3);
       matrice_aneidro_database(ind_dat+1,3)=mat_aneidro_definitiva(i,4);
       matrice_aneidro_database(ind_dat+1,4)=1;
       ind_dat=ind_dat+1;
   end
end

for i=1:size(matrice_aneidro_database,1)
    if matrice_aneidro_database(i,4)>24
        matrice_aneidro_database(i,4)=24;
    end
end
% =========================================================================
% =========================================================================
% ========================== DATABASE SONAR ===============================
% =========================================================================
% =========================================================================
% Matrice Sonar che identifica il numero di file regitrati
% nello stesso giorno. Dato che sono conteggiati anche i file zero se il
% numero eccede 24 impongo 24, tanto serve per una prima viasualizzazione!
matrice_sonar_database(1,1)=mat_sonar_definitiva(1,2);
matrice_sonar_database(1,2)=mat_sonar_definitiva(1,3);
matrice_sonar_database(1,3)=mat_sonar_definitiva(1,4);
matrice_sonar_database(1,4)=1;
ind_dat=1;

for i=2:size(mat_sonar_definitiva,1)
   if mat_sonar_definitiva(i,4)==mat_sonar_definitiva(i-1,4) % stesso anno
       if mat_sonar_definitiva(i,3)==mat_sonar_definitiva(i-1,3) % stesso mese
           if mat_sonar_definitiva(i,2)==mat_sonar_definitiva(i-1,2) % stesso giorno
               matrice_sonar_database(ind_dat,4)=matrice_sonar_database(ind_dat,4)+1;
               
           else % mantengo mese/anno e cambio giorno
               matrice_sonar_database(ind_dat+1,1)=mat_sonar_definitiva(i,2);
               matrice_sonar_database(ind_dat+1,2)=mat_sonar_definitiva(i-1,3);
               matrice_sonar_database(ind_dat+1,3)=mat_sonar_definitiva(i-1,4);
               matrice_sonar_database(ind_dat+1,4)=1;
               ind_dat=ind_dat+1;
           end
       else % mantengo anno cambio mese e giorno
           matrice_sonar_database(ind_dat+1,1)=mat_sonar_definitiva(i,2);
           matrice_sonar_database(ind_dat+1,2)=mat_sonar_definitiva(i,3);
           matrice_sonar_database(ind_dat+1,3)=mat_sonar_definitiva(i-1,4);
           matrice_sonar_database(ind_dat+1,4)=1;
           ind_dat=ind_dat+1;
       end
   else %cambio tutto
       matrice_sonar_database(ind_dat+1,1)=mat_sonar_definitiva(i,2);
       matrice_sonar_database(ind_dat+1,2)=mat_sonar_definitiva(i,3);
       matrice_sonar_database(ind_dat+1,3)=mat_sonar_definitiva(i,4);
       matrice_sonar_database(ind_dat+1,4)=1;
       ind_dat=ind_dat+1;
   end
end

for i=1:size(matrice_sonar_database,1)
    if matrice_sonar_database(i,4)>24
        matrice_sonar_database(i,4)=24;
    end
end
% =========================================================================
% =========================================================================
% ========================== DATABASE TELMA ===============================
% =========================================================================
% =========================================================================
% Matrice Telma che identifica il numero di immagini regitrate
% nello stesso giorno. Con i problemi di riavvio del sistema se il
% numero eccede 24 impongo 24, tanto serve per una prima viasualizzazione!
matrice_telma_database(1,1)=mat_telma(1,2);
matrice_telma_database(1,2)=mat_telma(1,3);
matrice_telma_database(1,3)=mat_telma(1,4);
matrice_telma_database(1,4)=1;
ind_dat=1;

for i=2:size(mat_telma,1)
   if mat_telma(i,4)==mat_telma(i-1,4) % stesso anno
       if mat_telma(i,3)==mat_telma(i-1,3) % stesso mese
           if mat_telma(i,2)==mat_telma(i-1,2) % stesso giorno
               matrice_telma_database(ind_dat,4)=matrice_telma_database(ind_dat,4)+1;
               
           else % mantengo mese/anno e cambio giorno
               matrice_telma_database(ind_dat+1,1)=mat_telma(i,2);
               matrice_telma_database(ind_dat+1,2)=mat_telma(i-1,3);
               matrice_telma_database(ind_dat+1,3)=mat_telma(i-1,4);
               matrice_telma_database(ind_dat+1,4)=1;
               ind_dat=ind_dat+1;
           end
       else % mantengo anno cambio mese e giorno
           matrice_telma_database(ind_dat+1,1)=mat_telma(i,2);
           matrice_telma_database(ind_dat+1,2)=mat_telma(i,3);
           matrice_telma_database(ind_dat+1,3)=mat_telma(i-1,4);
           matrice_telma_database(ind_dat+1,4)=1;
           ind_dat=ind_dat+1;
       end
   else %cambio tutto
       matrice_telma_database(ind_dat+1,1)=mat_telma(i,2);
       matrice_telma_database(ind_dat+1,2)=mat_telma(i,3);
       matrice_telma_database(ind_dat+1,3)=mat_telma(i,4);
       matrice_telma_database(ind_dat+1,4)=1;
       ind_dat=ind_dat+1;
   end
end

for i=1:size(matrice_telma_database,1)
    if matrice_telma_database(i,4)>24
        matrice_telma_database(i,4)=24;
    end
end
% =========================================================================
% =========================================================================
% ========================== DATABASE TELMO ===============================
% =========================================================================
% =========================================================================
% Matrice Telmo che identifica il numero di immagini regitrate
% nello stesso giorno. Con i problemi di riavvio del sistema se il
% numero eccede 24 impongo 24, tanto serve per una prima viasualizzazione!
matrice_telmo_database(1,1)=mat_telmo(1,2);
matrice_telmo_database(1,2)=mat_telmo(1,3);
matrice_telmo_database(1,3)=mat_telmo(1,4);
matrice_telmo_database(1,4)=1;
ind_dat=1;

for i=2:size(mat_telmo,1)
   if mat_telmo(i,4)==mat_telmo(i-1,4) % stesso anno
       if mat_telmo(i,3)==mat_telmo(i-1,3) % stesso mese
           if mat_telmo(i,2)==mat_telmo(i-1,2) % stesso giorno
               matrice_telmo_database(ind_dat,4)=matrice_telmo_database(ind_dat,4)+1;
               
           else % mantengo mese/anno e cambio giorno
               matrice_telmo_database(ind_dat+1,1)=mat_telmo(i,2);
               matrice_telmo_database(ind_dat+1,2)=mat_telmo(i-1,3);
               matrice_telmo_database(ind_dat+1,3)=mat_telmo(i-1,4);
               matrice_telmo_database(ind_dat+1,4)=1;
               ind_dat=ind_dat+1;
           end
       else % mantengo anno cambio mese e giorno
           matrice_telmo_database(ind_dat+1,1)=mat_telmo(i,2);
           matrice_telmo_database(ind_dat+1,2)=mat_telmo(i,3);
           matrice_telmo_database(ind_dat+1,3)=mat_telmo(i-1,4);
           matrice_telmo_database(ind_dat+1,4)=1;
           ind_dat=ind_dat+1;
       end
   else %cambio tutto
       matrice_telmo_database(ind_dat+1,1)=mat_telmo(i,2);
       matrice_telmo_database(ind_dat+1,2)=mat_telmo(i,3);
       matrice_telmo_database(ind_dat+1,3)=mat_telmo(i,4);
       matrice_telmo_database(ind_dat+1,4)=1;
       ind_dat=ind_dat+1;
   end
end

for i=1:size(matrice_telmo_database,1)
    if matrice_telmo_database(i,4)>24
        matrice_telmo_database(i,4)=24;
    end
end

% COMPILAZIONE MATRICE DATABASE ===========================================
global matrice_database;
matrice_database=zeros(60,31,10);
% righe: a blocchi di 5 identificano il mese di riferimento:
% 1 - GEN - Anemometro
% 2 - GEN - Idrometro
% 3 - GEN - ecosncadaglio
% 4 - GEN - telecamera ma
% 5 - GEN - telecamera mo
% 6 - FEB - Anemometro
% .......
% colonne: il numero dei giorni, max 31
% profondità gli anni di riferimento

% inserimento anometro/idrometro nella matrice 3D
for i=1:size(matrice_aneidro_database,1)
    giorno_db=matrice_aneidro_database(i,1);
    mese_db=matrice_aneidro_database(i,2);
    anno_db=matrice_aneidro_database(i,3)-2010;
    matrice_database(mese_db*5-4,giorno_db,anno_db)=matrice_aneidro_database(i,4);
    matrice_database(mese_db*5-3,giorno_db,anno_db)=matrice_aneidro_database(i,4);
end
% inserimento ecoscandaglio nella matrice 3D
for i=1:size(matrice_sonar_database,1)
    giorno_db=matrice_sonar_database(i,1);
    mese_db=matrice_sonar_database(i,2);
    anno_db=matrice_sonar_database(i,3)-2010;
    matrice_database(mese_db*5-2,giorno_db,anno_db)=matrice_sonar_database(i,4);
end
% inserimento telecamera Mantova nella matrice 3D
for i=1:size(matrice_telma_database,1)
    giorno_db=matrice_telma_database(i,1);
    mese_db=matrice_telma_database(i,2);
    anno_db=matrice_telma_database(i,3)-2010;
    matrice_database(mese_db*5-1,giorno_db,anno_db)=matrice_telma_database(i,4);
end
% inserimento telecamera Modena nella matrice 3D
for i=1:size(matrice_telmo_database,1)
    giorno_db=matrice_telmo_database(i,1);
    mese_db=matrice_telmo_database(i,2);
    anno_db=matrice_telmo_database(i,3)-2010;
    matrice_database(mese_db*5,giorno_db,anno_db)=matrice_telmo_database(i,4);
end
%==========================================================================
% PRIMA VISUALIZZAZIONE DELLA MATRICE DATABASE ============================
% settaggio mese e anno di dafault....
set(handles.popupmenu3,'Value',6); set(handles.popupmenu4,'Value',6);
% generazioni di variabili e acquisizione del mese e dell'anno...
global mese_scelto_utente; global anno_scelto_utente;
mese_scelto_utente=get(handles.popupmenu3,'Value');
anno_scelto_utente=get(handles.popupmenu4,'Value');
% definizione della variabile e creazione della matrice di zeri....
global table_db; table_db=zeros(5,31);
% compilazione della table_db con la scelta dell'utente di default...
for i=1:31
    table_db(1,i)=matrice_database(mese_scelto_utente*5-4,i,anno_scelto_utente);
    table_db(1,i)=matrice_database(mese_scelto_utente*5-3,i,anno_scelto_utente);
    table_db(1,i)=matrice_database(mese_scelto_utente*5-2,i,anno_scelto_utente);
    table_db(1,i)=matrice_database(mese_scelto_utente*5-1,i,anno_scelto_utente);
    table_db(1,i)=matrice_database(mese_scelto_utente*5,i,anno_scelto_utente);
end
set(handles.pushbutton5,'String','DATABASE OK');
set(handles.pushbutton5,'BackgroundColor',[0 1 0]);
set(handles.uitable1,'data',table_db);
%==========================================================================

% VISUALIZZA ==============================================================
% acquisisce la scelta del mese e dell'anno dell'utente, estrae e 
% visualizza i dati corrispondenti dalla matrice_database
% --- Executes on button press in pushbutton20.
function pushbutton20_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton20 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global matrice_database;
global mese_scelto_utente;
global anno_scelto_utente;
global table_db;

mese_scelto_utente=get(handles.popupmenu3,'Value');
anno_scelto_utente=get(handles.popupmenu4,'Value');

table_db=zeros(5,31);

for i=1:31
    table_db(1,i)=matrice_database(mese_scelto_utente*5-4,i,anno_scelto_utente);
    table_db(2,i)=matrice_database(mese_scelto_utente*5-3,i,anno_scelto_utente);
    table_db(3,i)=matrice_database(mese_scelto_utente*5-2,i,anno_scelto_utente);
    table_db(4,i)=matrice_database(mese_scelto_utente*5-1,i,anno_scelto_utente);
    table_db(5,i)=matrice_database(mese_scelto_utente*5,i,anno_scelto_utente);
end

set(handles.uitable1,'data',table_db);
set(handles.pushbutton20,'BackgroundColor',[0 1 0]);
%==========================================================================

% POPUPMENU "mese>>"=======================================================
% acquisisce il mese scelto dall'utente e cambia il colore del tasto
% visualizza per sottolinerare che l'utente ha cambiato mese e quindi per
% visualizzarlo deve rischiacciare il tasto visualizza.
% --- Executes on selection change in popupmenu3.
function popupmenu3_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = get(hObject,'String') returns popupmenu3 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu3
global mese_scelto_utente;
mese_scelto_utente=get(handles.popupmenu3,'Value');
set(handles.pushbutton20,'BackgroundColor',[1 0 0]);
%==========================================================================

% POPUPMENU "Anno>>"=======================================================
% acquisisce l'anno scelto dall'utente e cambia il colore del tasto
% visualizza per sottolinerare che l'utente ha cambiato anno e quindi per
% visualizzarlo deve rischiacciare il tasto visualizza.
% --- Executes on selection change in popupmenu4.
function popupmenu4_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = get(hObject,'String') returns popupmenu4 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu4
global anno_scelto_utente;
anno_scelto_utente=get(handles.popupmenu4,'Value');
set(handles.pushbutton20,'BackgroundColor',[1 0 0]);
%==========================================================================

%==================== BLOCCO (2) Scelta del giorno ========================
%==========================================================================
% DATA ?? =================================================================
% L'utente inserisce la data e il programma in automatico definisce il
% pacchetto di file da elaborare .....................................
% --- Executes on button press in pushbutton6.
function pushbutton6_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton6 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global minutick; global minutistr; global orack; global orastr;
global datagiorno; global datamese; global dataanno;
global giorno_utente; global mese_utente; global anno_utente;
global tabellamesitraslata;
global mat_check; global mat_telma; global mat_telmo;
global mat_aneidro_definitiva; global mat_sonar_definitiva;
global mat_aneidro; global mat_sonar;

% generazione di una variabile di controllo per uscire dal ciclo while
ck_dati_corretti=0;
while ck_dati_corretti<5
    % i comandi per il box di inserimento dati che compare sul PC......
    prompt={'inserire il giorno:';'inserire il mese:';'inserire anno:';'inserire ora:';'inserire minuti:'};
    title='Impostare data/ora di visualizzazione';
    answer=inputdlg(prompt,title); giorno_utente = str2num(answer{1}); 
    mese_utente = str2num(answer{2}); anno_utente = str2num(answer{3});
    ora_utente = str2num(answer{4}); minuti_utente = str2num(answer{5});
    % check anno utente ===================================================
    if anno_utente>=2011 && anno_utente<=2020
        ck_anno_utente=1;
        % devo valutare se l'anno inserito è bisestile per fare un check
        % sul giorno e sul mese (Febbraio!!!!)
        if mod(anno_utente,4)==0 && (mod(anno_utente,100)~=0 || mod(anno_utente,400)==0)
            ck_bisestile=1;
        else
            ck_bisestile=0;
        end    
    else
        ck_anno_utente=0;
    end
    % check ora utente ====================================================
    if ora_utente>=0 && ora_utente<=23
        ck_ora_utente=1;
    else
        ck_ora_utente=0;
    end
    % check minuti utente =================================================
    if minuti_utente>=0 && minuti_utente<=59
        ck_min_utente=1;
    else
        ck_min_utente=0;
    end
    % check mese utente ===================================================
    if mese_utente>=1 && mese_utente<=12
        ck_mese_utente=1;
    else
        ck_mese_utente=0;
    end
    % check giorno utente =================================================
    if mese_utente==2
        if ck_bisestile==1
            if giorno_utente>=1 && giorno_utente<=29
                ck_gg_utente=1;
            else
                ck_gg_utente=0;
            end
        else
           if giorno_utente>=1 && giorno_utente<=28
                ck_gg_utente=1;
           else
                ck_gg_utente=0;
           end
        end
    end
    if mese_utente~=2
        if giorno_utente>=1 && giorno_utente<=31
                ck_gg_utente=1;
            else
                ck_gg_utente=0;
        end
    end
ck_dati_corretti=ck_anno_utente+ck_mese_utente+ck_ora_utente+...
    ck_min_utente+ck_gg_utente;
end
% uscito dal ciclo il bottone diventa verde, data accettata!!!!!!!!!!!!!!!
set(handles.pushbutton6,'BackgroundColor',[0 1 0]);

% acquisiti i dati devo sostituirli con quelli presenti di default,
% ovverosia la "data odierna" e "l'ora corrente"
datagiorno=num2str(giorno_utente); datamese=num2str(mese_utente);
dataanno=num2str(anno_utente);
orack=ora_utente; minutick=minuti_utente;
orastr=num2str(ora_utente); minutistr=num2str(minuti_utente);

% una volta cambiata dal data si deve aggiornare tutto il pacchetto....
% ovveorsia: determinazione del giorno della settimana associato

% creazione della stringa di visualizzazione
dataodierna=strcat(datagiorno,'/',datamese,'/',dataanno);
set(handles.text2,'string',dataodierna);
set(handles.text2,'ForegroundColor',[1 0 0]);
% se i minuti sono < 10 inserisco uno zero davanti, per una più corretta
% visualizzazione, anche per l'utente
if minutick<10
    oraodierna=strcat(orastr,':','0',minutistr);
else
    oraodierna=strcat(orastr,':',minutistr);
end
set(handles.text9,'string',oraodierna);
set(handles.text9,'ForegroundColor',[1 0 0]);
% consideriamo una data di riferimento: 1-gen-1928, DOMENICA
dataannonum=str2num(dataanno);
d=dataannonum-1928;
% divido annodifferenza per 4 ottenedo il quoziente e il resto
quoz=fix(d/4); resto=mod(d,4);
if resto==0
    d=d+quoz;
else
    d=d+quoz+1;
end
resto_d=mod(d,7);
% il primo passo è la determinazione dell giorno della settimana del primo
% giorno dell'anno scelto dall'utente. Creao una corrisponde biunivoca tra
% i giorni della settimana e dei numeri progressivi.
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch resto_d
    case 0
        ggprimoanno='Dom'; % 0=domenica
    case 1
        ggprimoanno='Lun'; % 1=lunedì
    case 2
        ggprimoanno='Mar'; % 2=martedì
    case 3
        ggprimoanno='Mer'; % 3=mercoledì
    case 4
        ggprimoanno='Gio'; % 4=giovedì
    case 5
        ggprimoanno='Ven'; % 5=venerdì
    case 6
        ggprimoanno='Sab'; % 6=sabato
end
%==========================================================================
% il secondo passo è la determinazione del giorno della settinama del primo
% giorno del mese scelto dall'utente.
% La tabella di riferimento che ho trovato in un esecizio in internet 
% associa il numero al giorno della settimana come la convenzione appena 
% citata (0=Dom, ecc...).

%  num     giorno    anno non bisestile       anno bisestile
%   0       Dom          Gen Ott              Gen Apr Lug
%   1       Lun          Mag                  Ott
%   2       Mar          Ago                  Mag
%   3       Mer          Feb Mar Nov          Feb Ago
%   4       Gio          Giu                  Mar Nov
%   5       Ven          Set Dic              Giu
%   6       Sab          Apr Lug              Set Dic

% creazione della tabella
tabellamesi(1,1)=1;   tabellamesi(1,2)=0;  tabellamesi(1,3)=0;
tabellamesi(2,1)=2;   tabellamesi(2,2)=3;  tabellamesi(2,3)=3;
tabellamesi(3,1)=3;   tabellamesi(3,2)=3;  tabellamesi(3,3)=4;
tabellamesi(4,1)=4;   tabellamesi(4,2)=6;  tabellamesi(4,3)=0;
tabellamesi(5,1)=5;   tabellamesi(5,2)=1;  tabellamesi(5,3)=2;
tabellamesi(6,1)=6;   tabellamesi(6,2)=4;  tabellamesi(6,3)=5;
tabellamesi(7,1)=7;   tabellamesi(7,2)=6;  tabellamesi(7,3)=0;
tabellamesi(8,1)=8;   tabellamesi(8,2)=2;  tabellamesi(8,3)=3;
tabellamesi(9,1)=9;   tabellamesi(9,2)=5;  tabellamesi(9,3)=6;
tabellamesi(10,1)=10; tabellamesi(10,2)=0; tabellamesi(10,3)=1;
tabellamesi(11,1)=11; tabellamesi(11,2)=3; tabellamesi(11,3)=4;
tabellamesi(12,1)=12; tabellamesi(12,2)=5; tabellamesi(12,3)=6;
% questa tabella consente di associare direttamente il giorno della
% settimana del primo giorno del generico mese se il primo giorno dell'anno
% cade in domenica (1/1/1928). Se vogliamo trasformarla in una tabella
% dinamica dobbiamo intrdurre un coefficnete che trasli i giorni in base al
% giorno della settimana con cui comincia l'anno corrente.


for i=1:12
    tabellamesitraslata(i,1)=tabellamesi(i,1);
    tabellamesitraslata(i,2)=tabellamesi(i,2)+resto_d;
    tabellamesitraslata(i,3)=tabellamesi(i,3)+resto_d;
    if tabellamesitraslata(i,2)>6
        tabellamesitraslata(i,2)=tabellamesitraslata(i,2)-7;
    end
    if tabellamesitraslata(i,3)>6
        tabellamesitraslata(i,3)=tabellamesitraslata(i,3)-7;
    end
end
% considerando che ci sono differenze a seconda che l'anno sia o mneo
% bisestile, dobbiamo capire se l'anno scelto dall'utente è bisestile
if mod(dataannonum,4)==0 && (mod(dataannonum,100)~=0 || mod(dataannonum,400)==0)
    bisestile=1;
else
    bisestile=0;
end    
% utilizzo della colonna bisestile o non a seconda del valore di "bisestile"
datamesenum=str2num(datamese);

for i=1:12
    if datamesenum==tabellamesitraslata(i,1)
        primomese=tabellamesitraslata(i,2+bisestile);
    end
end
% l'associazione numero-giorno parte da Domenica:
% 0=Dom // 1=Lun // 2=Mar // 3=Mer // 4=Gio // 5=Ven // 6=Sab
switch primomese    
    case 0
        ggprimomese='Dom'; % 0=domenica
    case 1
        ggprimomese='Lun'; % 1=lunedì
    case 2
        ggprimomese='Mar'; % 2=martedì
    case 3
        ggprimomese='Mer'; % 3=mercoledì
    case 4
        ggprimomese='Gio'; % 4=giovedì
    case 5
        ggprimomese='Ven'; % 5=venerdì
    case 6
        ggprimomese='Sab'; % 6=sabato
end
%==========================================================================
tabellagiorni(1,1)=1; tabellagiorni(1,2)=primomese;

for i=2:31
    tabellagiorni(i,1)=i;
    tabellagiorni(i,2)=tabellagiorni(i-1,2)+1;
    if tabellagiorni(i,2)==7
        tabellagiorni(i,2)=0;
    end
end

datagiornonum=str2num(datagiorno);

for i=1:31
    if datagiornonum==tabellagiorni(i,1)
        ggesatto=tabellagiorni(i,2);
    end
end

switch ggesatto
    case 0
        ggrichiesto='Dom'; % 0=domenica
    case 1
        ggrichiesto='Lun'; % 1=lunedì
    case 2
        ggrichiesto='Mar'; % 2=martedì
    case 3
        ggrichiesto='Mer'; % 3=mercoledì
    case 4
        ggrichiesto='Gio'; % 4=giovedì
    case 5
        ggrichiesto='Ven'; % 5=venerdì
    case 6
        ggrichiesto='Sab'; % 6=sabato
end
set(handles.text10,'string',ggrichiesto);
set(handles.text10,'ForegroundColor',[1 0 0]);
% inserita la data si devono identificare i file utili per l'elaborazione

% data di riferimento: datagiorno/datamese/dataanno
gg_rif=str2num(datagiorno); mm_rif=str2num(datamese);
an_rif=str2num(dataanno);
global ora_rif; global min_rif;
ora_rif=orack; min_rif=minutick;

% partendo dalle tabelle generate per il database si deve aggiungere 
% una colonna che identifica il file da elaborare.....
% 100 = da elaborare 200 = non elaborare

global mat_check_link; global mat_telma_link; global mat_telmo_link;
global mat_aneidro_link; global mat_sonar_link;
% =========================================================================
% matrice dei file di check ===============================================
% matrice di partenza: mat_check (l'unica creata)
for i=1:size(mat_check,1)
    mat_check_link(i,1)=mat_check(i,1);
    mat_check_link(i,2)=mat_check(i,2);
    mat_check_link(i,3)=mat_check(i,3);
    mat_check_link(i,4)=mat_check(i,4);
    mat_check_link(i,5)=200;
    if mat_check(i,4)==an_rif && mat_check(i,3)==mm_rif && mat_check(i,2)==gg_rif
        mat_check_link(i,5)=100;
    end
end
%==========================================================================
% matrice telecamera Mantova ==============================================
% matrice di partenza: mat_telma (l'unica creata)
for i=1:size(mat_telma,1)
    mat_telma_link(i,1)=mat_telma(i,1); mat_telma_link(i,2)=mat_telma(i,2);
    mat_telma_link(i,3)=mat_telma(i,3); mat_telma_link(i,4)=mat_telma(i,4);
    mat_telma_link(i,5)=mat_telma(i,5); mat_telma_link(i,6)=mat_telma(i,6);
    mat_telma_link(i,7)=mat_telma(i,7);
    mat_telma_link(i,8)=200;
    if mat_telma(i,4)==an_rif && mat_telma(i,3)==mm_rif && mat_telma(i,2)==gg_rif
        if mat_telma(i,5)<ora_rif
            mat_telma_link(i,8)=100;
        end
        if mat_telma(i,5)==ora_rif && mat_telma(i,6)<= min_rif
            mat_telma_link(i,8)=100;
        end
    end
end
%==========================================================================
% matrice telecamera Modena ===============================================
% matrice di partenza: mat_telmo (l'unica creata)
for i=1:size(mat_telmo,1)
    mat_telmo_link(i,1)=mat_telmo(i,1); mat_telmo_link(i,2)=mat_telmo(i,2);
    mat_telmo_link(i,3)=mat_telmo(i,3); mat_telmo_link(i,4)=mat_telmo(i,4);
    mat_telmo_link(i,5)=mat_telmo(i,5); mat_telmo_link(i,6)=mat_telmo(i,6);
    mat_telmo_link(i,7)=mat_telmo(i,7); 
    mat_telmo_link(i,8)=200;
    if mat_telmo(i,4)==an_rif && mat_telmo(i,3)==mm_rif && mat_telmo(i,2)==gg_rif
        if mat_telmo(i,5)<ora_rif
            mat_telmo_link(i,8)=100;
        end
        if mat_telmo(i,5)==ora_rif && mat_telmo(i,6)<= min_rif
            mat_telmo_link(i,8)=100;
        end
    end
end
%==========================================================================
% matrice Anemometro e Idrometro ==========================================
% matrice di partenza: mat_aneidro_definitiva (l'ultima creata)
for i=1:size(mat_aneidro_definitiva,1)
    mat_aneidro_link(i,1)=mat_aneidro_definitiva(i,1);
    mat_aneidro_link(i,2)=mat_aneidro_definitiva(i,2);
    mat_aneidro_link(i,3)=mat_aneidro_definitiva(i,3);
    mat_aneidro_link(i,4)=mat_aneidro_definitiva(i,4);
    mat_aneidro_link(i,5)=mat_aneidro_definitiva(i,5);
    mat_aneidro_link(i,6)=mat_aneidro_definitiva(i,6);
    mat_aneidro_link(i,7)=mat_aneidro_definitiva(i,7); 
    mat_aneidro_link(i,8)=200;
    if mat_aneidro_definitiva(i,4)==an_rif && mat_aneidro_definitiva(i,3)==mm_rif && mat_aneidro_definitiva(i,2)==gg_rif
        if mat_aneidro_definitiva(i,5)<ora_rif
            mat_aneidro_link(i,8)=100;
        end
        if mat_aneidro_definitiva(i,5)==ora_rif && mat_aneidro_definitiva(i,6)<= min_rif
            mat_aneidro_link(i,8)=100;
        end
    end
end
%==========================================================================
% matrice Sonar ===========================================================
% matrice di partenza: mat_sonar_definitiva (l'ultima creata)
for i=1:size(mat_sonar_definitiva,1)
    mat_sonar_link(i,1)=mat_sonar_definitiva(i,1);
    mat_sonar_link(i,2)=mat_sonar_definitiva(i,2);
    mat_sonar_link(i,3)=mat_sonar_definitiva(i,3);
    mat_sonar_link(i,4)=mat_sonar_definitiva(i,4);
    mat_sonar_link(i,5)=mat_sonar_definitiva(i,5);
    mat_sonar_link(i,6)=mat_sonar_definitiva(i,6);
    mat_sonar_link(i,7)=mat_sonar_definitiva(i,7); 
    mat_sonar_link(i,8)=200;
    if mat_sonar_definitiva(i,4)==an_rif && mat_sonar_definitiva(i,3)==mm_rif && mat_sonar_definitiva(i,2)==gg_rif
        if mat_sonar_definitiva(i,5)<ora_rif
            mat_sonar_link(i,8)=100;
        end
        if mat_sonar_definitiva(i,5)==ora_rif && mat_sonar_definitiva(i,6)<= min_rif
            mat_sonar_link(i,8)=100;
        end
    end
end
% =========================================================================
% Cancellazione delle variabili globali e rigenerazione delle stesse.
% Questa operazione è necessaria se l'utente vuole, dopo aver valutato una
% giornata, cambiare giorno. In questo caso le matrici utilizzate per il
% caso precedente vengono sovrascritte con le informazioni della nuova
% data. Se però i file sono in numero inferiore, nella matrice rimangono
% tracce dei file precedenti falsando la visualizzazione..................

clear -global mat_aneidro_elab;    clear -global mat_sonar_elab;
clear -global mat_telma_elab;      clear -global mat_telmo_elab;
clear -global mat_check_elab;

global mat_aneidro_elab; global mat_sonar_elab; global mat_telma_elab; 
global mat_telmo_elab; global mat_check_elab;

ind_mat=1;
for i=1:size(mat_aneidro_link,1) % ANEMOMETRO =============================
    if mat_aneidro_link(i,8)==100
        for j=1:8
        mat_aneidro_elab(ind_mat,j)=mat_aneidro_link(i,j);    
        end
        ind_mat=ind_mat+1;
    end
end
ind_mat=1;
for i=1:size(mat_sonar_link,1) % SONAR ====================================
    if mat_sonar_link(i,8)==100
        for j=1:8
        mat_sonar_elab(ind_mat,j)=mat_sonar_link(i,j);
        end
        ind_mat=ind_mat+1;
    end
end
ind_mat=1;
for i=1:size(mat_telma_link,1) % TELECAMERA MANOTVA =======================
    if mat_telma_link(i,8)==100
        for j=1:8
        mat_telma_elab(ind_mat,j)=mat_telma_link(i,j);
        end
        ind_mat=ind_mat+1;
    end
end
ind_mat=1;
for i=1:size(mat_telmo_link,1) % TELECAMERA MODENA ========================
    if mat_telmo_link(i,8)==100
        for j=1:8
        mat_telmo_elab(ind_mat,j)=mat_telmo_link(i,j);
        end
        ind_mat=ind_mat+1;
    end
end
ind_mat=1;
for i=1:size(mat_check_link,1) % CHECK ====================================
    if mat_check_link(i,5)==100
        for j=1:5
        mat_check_elab(ind_mat,j)=mat_check_link(i,j);
        end
        ind_mat=ind_mat+1;
    end
end
%==========================================================================



%=========== BLOCCO (3) Analisi oraria dati di Borgoforte .... ============
% ANALISI ORARIA ==========================================================
% In questo blocco si recuperano le informazioni da matrici precendetemente
% compilate per estrarre delle sottomatrici contenenti solo i file che
% dovranno essere elaborati. Da precisare che nello smistamento dei file
% rientrano anche i file "zero", ovverosia i file che il software di 
% Borgoforte salva quando si riavvia. Ovviamente questi file anche se
% rientrano nell'intervallo di analisi dei dati non hanno nessun valore,
% quindi devono essere eliminati dalla lista....
% 
% --- Executes on button press in pushbutton8.
function pushbutton8_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton8 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% =========================================================================
% cancellazione variabili sensibili
clear e ee eee 
clear eee_aneidro eee_sonar eee_ma eee_mo eee_check

global mat_check_elab;   global mat_telma_elab; global mat_telmo_elab;
global mat_aneidro_elab; global mat_sonar_elab;

clear -global tab_vento;     clear -global tab_sonar;
clear -global tab_ane;       clear -global tab_idro;
clear -global tab;           clear -global tab_medie;
clear -global tab_max;           clear -global tab_var;

global tab_vento;   global tab_sonar;   global tab_ane; global tab_idro;

global tab_medie;   global tab; global tab_max; global tab_var;

clear -global matrice_totale_sonar;     clear -global matrice_totale_idro;
clear -global matrice_totale_ane;       

global matrice_totale_sonar; global matrice_totale_ane;
global matrice_totale_idro;

%==========================================================================
% Procedimento generale
% 1) rigenerazione del nome del file e creazione struttura 
%    con i file da analizzare (1 per ogni strumento)
% 2) analizzare i file

% 1)=======================================================================

global cod_prog; global arrivo; global cod_setup; global checktable99;

% =========================================================================
% creao matrice di visualizzazione con il numero 99. L'idea è quella di
% plottare questa matrice nella tabella del blocco (3) in modo che l'tente
% veda dei numeri che però non hanno senso (99) in modo da ricordargli
% visivamente che deve cliccare su check dati?....questa operazione è stata
% creata per l'utilizzo continuativo del programma , ovverosia se si vuole
% cambiare data di visualizzazione. In questo modo quando si schiaccia
% Analisi Oraria, la tabella si popola di 99 cancellando le informazioni
% precedenti e forzando l'tente ad una nuova visualizzazione.
for i=1:24
    for j=1:5
        checktable99(i,j)=99;
    end
end
set(handles.uitable2,'data',checktable99);
% =========================================================================
% settaggio cartella di arrivo dati. Se il programma viene utilizzato in
% continuo, mi assicuro che l'operazione di analisi file venga fatta nella
% cartella giusta.
cd(arrivo);
% =========================================================================
dascrivere='In progress...';
set(handles.text12,'String',dascrivere);
pause(1);
e=dir;
for i=3:size(e,1)
    ee(i-2,1)=e(i,1); % eliminazione dei primi due ("." "..")
end
indice=1;
 for i=1:size(ee,1)
     if ee(i).bytes >0 % eliminazione dei file 0 bytes (riavvio sistema)
     eee(indice,1)=ee(i,1); % contiene l'elenco dei file "buoni"
     indice=indice+1;
     end
 end    

 % eliminazione da eee dei file che non devono essere elaborati. Confronto
 % i nomi con le matrici "_elab" e cancello.....
 
 num_aneidro=1; num_sonar=1; num_ma=1; num_mo=1; num_ck=1;
 
 for i=1:size(eee,1) % Eliminazione ANEIDRO inutili
     pezzofile=eee(i).name(1:2);
     switch pezzofile
         case 'an'
             for j=1:size(mat_aneidro_elab,1)
                 tempo=num2str(mat_aneidro_elab(j,1));
                 nome_file=strcat('analog',tempo,'.txt');
                 nome_elenco=eee(i).name;
                 TF=strcmp(nome_elenco,nome_file);
                 if TF==1
                     file_trasferire=eee(i,1);
                     eee_aneidro(num_aneidro,1)=file_trasferire;
                     num_aneidro=num_aneidro+1;
                 end
             end
         case 'so'
            for j=1:size(mat_sonar_elab,1)
                 tempo=num2str(mat_sonar_elab(j,1));
                 nome_file=strcat('sonar',tempo,'.txt');
                 nome_elenco=eee(i).name;
                 TF=strcmp(nome_elenco,nome_file);
                 if TF==1
                     file_trasferire=eee(i,1);
                     eee_sonar(num_sonar,1)=file_trasferire;
                     num_sonar=num_sonar+1;
                 end
             end
         case 'ma'
            for j=1:size(mat_telma_elab,1)
                 tempo=num2str(mat_telma_elab(j,1));
                 nome_file=strcat('mantova',tempo,'.jpg');
                 nome_elenco=eee(i).name;
                 TF=strcmp(nome_elenco,nome_file);
                 if TF==1
                     file_trasferire=eee(i,1);
                     eee_ma(num_ma,1)=file_trasferire;
                     num_ma=num_ma+1;
                 end
             end
         case 'mo'
             for j=1:size(mat_telmo_elab,1)
                 tempo=num2str(mat_telmo_elab(j,1));
                 nome_file=strcat('modena',tempo,'.jpg');
                 nome_elenco=eee(i).name;
                 TF=strcmp(nome_elenco,nome_file);
                 if TF==1
                     file_trasferire=eee(i,1);
                     eee_mo(num_mo,1)=file_trasferire;
                     num_mo=num_mo+1;
                 end
             end
         case 'ro'
             for j=1:size(mat_check_elab,1)
                 ck_g=num2str(mat_check_elab(j,2));
                 ck_m=num2str(mat_check_elab(j,3));
                 ck_a=num2str((mat_check_elab(j,4))-2000);
                 nome_file=strcat('router',ck_g,'-',ck_m,'-',ck_a,'.txt');
                 nome_elenco=eee(i).name;
                 TF=strcmp(nome_elenco,nome_file);
                 if TF==1
                     file_trasferire=eee(i,1);
                     eee_check(num_ck,1)=file_trasferire;
                     num_ck=num_ck+1;
                 end
             end      
     end % fine switch   
 end

clear -global matrice_analisi_aneidro;
clear -global matrice_analisi_sonar;
clear -global matrice_analisi_telma;
clear -global matrice_analisi_telmo;
clear -global matrice_analisi_check;

global matrice_analisi_aneidro; global matrice_analisi_sonar;
global matrice_analisi_telma; global matrice_analisi_telmo;
global matrice_analisi_check;
% =========================================================================
% 2)=======================================================================
% =========================================================================
% ANEMOMETRO E IDROMETRO ==================================================
for i=1:size(mat_aneidro_elab,1)
    stringafile=num2str(mat_aneidro_elab(i,1));
    nomefile=strcat('analog',stringafile,'.txt');
    for j=1:size(eee_aneidro,1)
        if nomefile==eee_aneidro(j).name
            if eee(j).bytes>0
                matrice_analisi_aneidro(j,1)=mat_aneidro_elab(i,1);
                matrice_analisi_aneidro(j,2)=mat_aneidro_elab(i,2);
                matrice_analisi_aneidro(j,3)=mat_aneidro_elab(i,3);
                matrice_analisi_aneidro(j,4)=mat_aneidro_elab(i,4);
                matrice_analisi_aneidro(j,5)=mat_aneidro_elab(i,5);
                matrice_analisi_aneidro(j,6)=mat_aneidro_elab(i,6);
                matrice_analisi_aneidro(j,7)=mat_aneidro_elab(i,7);
            end
        end
    end
end
% estrazione dei dati e conversione, il file aneidro ha 4 colonne:
% 1) colonna anemometro velocità
% 2) colonna idrometro
% 3) colonna anemometro direzione
% 4) tempo di acquisizione
x=1;
for i=1:size(eee_aneidro,1)
    % con la funzione textread leggo il primo file e creo una matrice che
    % ha una sola colonna. I valori nelle righe rappresentano a seconda
    % della riga una delle 4 informazioni riportate sopra....
    % riga 1: anemometro velocità
    % riga 2: idrometro
    % riga 3: anemometro direzione
    % riga 4: tempo di acquisizione
    % ricomnicio....
    % riga 5: anemometro velocità
    % riga 6: così via....
    z=textread(eee_aneidro(i).name,'%n');
    h=1;
    % ciclo per leggere i file di z e creare una matrice in cui i diversi
    % valori siano in colonne differenti....
    for j=1:4:size(z,1)-3
        % prima riga e poi multiple di 4....anemometro velocità
        tab(h,1)=((z(j,1)*1000)-4)*3.75;
        % terza riga e poi multiple di 4....anemometro direzione
        tab(h,2)=((z(j+2,1)*1000)-4)*22.5;
        % seconda riga e poi multiple di 4....idrometro
        tab(h,3)=(29.86-(20+((z(j+1,1)*1000)-4)*(-1.28)));
        % quarta riga e poi multiple di 4....tempo acquisizione
        tab(h,4)=z(j+3,1)-z(4,1);
        h=h+1;
    end
    % alla fine di questo ciclo for ho una matrice con 4 colonne
    %  vel_ane     dir_ane     idrometro      tempo
    %   ....        ....         .....         ...
    % realizzazione della tabella medie orarie (servono per i calcoli)
    tab_medie(i,1)=mean(tab(:,1),1); % anemometro velocità
    tab_medie(i,2)=mean(tab(:,2),1); % anemometro direzione
    tab_medie(i,3)=mean(tab(:,3),1); % idrometro
    tab_medie(i,4)=matrice_analisi_aneidro(i,2);
    tab_medie(i,5)=matrice_analisi_aneidro(i,3);
    tab_medie(i,6)=matrice_analisi_aneidro(i,4);
    tab_medie(i,7)=matrice_analisi_aneidro(i,5);
    tab_medie(i,8)=matrice_analisi_aneidro(i,6);
    tab_medie(i,9)=matrice_analisi_aneidro(i,7);
    % realizzazione della tabella dei massimi e direzione associata
    tab_max(i,1)=max(tab(:,1));
    for o=1:size(tab,1)
        if tab(o,1)==tab_max(i,1)
            tab_max(i,2)=tab(o,2);
        end
    end
    % realizzazione della tabella varianze orarie
    tab_var(i,1)=var(tab(:,1),1); % anemometro velocità
    tab_var(i,2)=var(tab(:,2),1); % anemometro direzione
    tab_var(i,3)=var(tab(:,3),1); % idrometro
    tab_var(i,4)=matrice_analisi_aneidro(i,2);
    tab_var(i,5)=matrice_analisi_aneidro(i,3);
    tab_var(i,6)=matrice_analisi_aneidro(i,4);
    tab_var(i,7)=matrice_analisi_aneidro(i,5);
    tab_var(i,8)=matrice_analisi_aneidro(i,6);
    tab_var(i,9)=matrice_analisi_aneidro(i,7);
    % realizzare medie/var/max per il vento ogni 10 min secondo normativa 
    % per una visualizzazione dell'andamento del vento.....
    
    % 1A colonna: numerazione progressiva
    tab_vento(x,1) = i;
    tab_vento(x+1,1)=i+0.166;
    tab_vento(x+2,1)=i+0.333;
    tab_vento(x+3,1)=i+0.5;
    tab_vento(x+4,1)=i+0.666;
    tab_vento(x+5,1)=i+0.833;
    % 2A colonna: media/10 min velocità vento
    tab_vento(x,2) = mean(tab(1:600,1),1);
    tab_vento(x+1,2)=mean(tab(601:1200,1),1);
    tab_vento(x+2,2)=mean(tab(1201:1800,1),1);
    tab_vento(x+3,2)=mean(tab(1801:2400,1),1);
    tab_vento(x+4,2)=mean(tab(2401:3000,1),1);
    tab_vento(x+5,2)=mean(tab(3001:3600,1),1);
    % 3A colonna: media/10 min direzione vento
    tab_vento(x,3) = mean(tab(1:600,2),1);
    tab_vento(x+1,3)=mean(tab(601:1200,2),1);
    tab_vento(x+2,3)=mean(tab(1201:1800,2),1);
    tab_vento(x+3,3)=mean(tab(1801:2400,2),1);
    tab_vento(x+4,3)=mean(tab(2401:3000,2),1);
    tab_vento(x+5,3)=mean(tab(3001:3600,2),1);
    % 4A colonna: varianza/10 min direzione vento
    tab_vento(x,4)=var(tab(1:600,1),1);
    tab_vento(x+1,4)=var(tab(601:1200,1),1);
    tab_vento(x+2,4)=var(tab(1201:1800,1),1);
    tab_vento(x+3,4)=var(tab(1801:2400,1),1);
    tab_vento(x+4,4)=var(tab(2401:3000,1),1);
    tab_vento(x+5,4)=var(tab(3001:3600,1),1);
    % 5A colonna: max/10 min direzione vento (folata)
    tab_vento(x,5) = max(tab(1:600,1));
    tab_vento(x+1,5)=max(tab(601:1200,1));
    tab_vento(x+2,5)=max(tab(1201:1800,1));
    tab_vento(x+3,5)=max(tab(1801:2400,1));
    tab_vento(x+4,5)=max(tab(2401:3000,1));
    tab_vento(x+5,5)=max(tab(3001:3600,1));
    % 6A colonna: direzione associata al max(folata)
    for k=1:size(tab,1)
        if tab_vento(x,5)==tab(k,1)
            tab_vento(x,6)=tab(k,2);
        end
        if tab_vento(x+1,5)==tab(k,1)
            tab_vento(x+1,6)=tab(k,2);
        end
        if tab_vento(x+2,5)==tab(k,1)
            tab_vento(x+2,6)=tab(k,2);
        end
        if tab_vento(x+3,5)==tab(k,1)
            tab_vento(x+3,6)=tab(k,2);
        end
        if tab_vento(x+4,5)==tab(k,1)
            tab_vento(x+4,6)=tab(k,2);
        end
        if tab_vento(x+5,5)==tab(k,1)
            tab_vento(x+5,6)=tab(k,2);
        end
    end
    x=x+6;
end

for i=1:size(matrice_analisi_aneidro,1)
    timing=matrice_analisi_aneidro(i,5)+matrice_analisi_aneidro(i,6)/60+...
    matrice_analisi_aneidro(i,7)/3600;
    tab_vento((1+(i-1)*6),7)=timing;
end

for i=1:6:(size(tab_vento,1)-5)
    tab_vento(i+1,7)=tab_vento(i,7)+0.166;
    tab_vento(i+2,7)=tab_vento(i,7)+0.333;
    tab_vento(i+3,7)=tab_vento(i,7)+0.5;
    tab_vento(i+4,7)=tab_vento(i,7)+0.666;
    tab_vento(i+5,7)=tab_vento(i,7)+0.833;
end

for i=1:size(tab_medie,1)
    tab_idro(i,1)=tab_medie(i,3);
    tab_idro(i,2)=tab_var(i,3);
end

% matrice riassuntiva anemometro (tab_ane)
for i=1:size(tab_medie,1)
    tab_ane(i,1)=tab_medie(i,1); % velocità media vento (1 ora)
    tab_ane(i,2)=tab_medie(i,2); % direzione media vento (1 ora)
    tab_ane(i,3)=tab_var(i,1); % varianza velocità (1 ora)
    tab_ane(i,4)=tab_var(i,2); % varianza direzione (1 ora)
    tab_ane(i,5)=tab_max(i,1); % velocità max (1 ora)
    tab_ane(i,6)=tab_max(i,2);  % direzione associata a Vmax
end
% MATRICE_TOTALE_ANE ======================================================
for i=1:size(tab_ane,1)
    for j=1:14
        if j<=8
            if j==8
                matrice_totale_ane(i,j)=matrice_analisi_aneidro(i,5)+...
                matrice_analisi_aneidro(i,6)/60+...
                matrice_analisi_aneidro(i,7)/3600;
            else
                matrice_totale_ane(i,j)=matrice_analisi_aneidro(i,j);
            end
        end
        if j>8
            matrice_totale_ane(i,j)=tab_ane(i,j-8);
        end
    end
end
% MATRICE_TOTALE_IDRO =====================================================
for i=1:size(tab_idro,1)
    for j=1:10
        if j<=8
            if j==8
                matrice_totale_idro(i,j)=matrice_analisi_aneidro(i,5)+...
                matrice_analisi_aneidro(i,6)/60+...
                matrice_analisi_aneidro(i,7)/3600;
            else
                matrice_totale_idro(i,j)=matrice_analisi_aneidro(i,j);
            end
        end
        if j>8
            matrice_totale_idro(i,j)=tab_idro(i,j-8);
        end
    end
end
% SONAR ===================================================================
for i=1:size(mat_sonar_elab,1)
    stringafile=num2str(mat_sonar_elab(i,1));
    nomefile=strcat('sonar',stringafile,'.txt');
    for j=1:size(eee_sonar,1)
        if nomefile==eee_sonar(j).name
            if eee(j).bytes>0
                matrice_analisi_sonar(j,1)=mat_sonar_elab(i,1);
                matrice_analisi_sonar(j,2)=mat_sonar_elab(i,2);
                matrice_analisi_sonar(j,3)=mat_sonar_elab(i,3);
                matrice_analisi_sonar(j,4)=mat_sonar_elab(i,4);
                matrice_analisi_sonar(j,5)=mat_sonar_elab(i,5);
                matrice_analisi_sonar(j,6)=mat_sonar_elab(i,6);
                matrice_analisi_sonar(j,7)=mat_sonar_elab(i,7);
            end
        end
    end
end

k=1;
h=1;
E=0;
nodato=0;

% estrazione dei dati e conversione
for i=1:size(eee_sonar,1)
    numero=0;
    % legge uno per volta i file contenuti nell'elenco
    z=textread(eee_sonar(i).name,'%s');
    % questa tipologia di lettura crea un matrice con una sola colonna in
    % cui sono elencati in sequenza i numeri letti nel txt, ovverosia
    % alcuni sono i dati del sonar altri sono le codifiche tempo labview
    for j=1:size(z,1)
        % leggo il primo valore del singolo file. Il sonar salva il valore
        % con una "R" al primo posto, poi il numero in metri ed
        % eventualmente una "E" alla fine per indicare che il dato è
        % incerto....
        % codifica R99.99E vuol dire che non ha ricevuto ritorno
        if numel(z{j,1})~=0    
        primo=z{j,1}(1);
        % ================================================================
        % le casistiche affrontate sono in continuo upgrade in base alle
        % anomalie che man mano si riscontrano nei dati restituiti 
        % dal sonar.....
        % ================================================================
        % identificazione del carattere 
        stringacontrollo=z{j,1};
        % char(0:255) visualizza tutti i simboli....il 14 è quello che
        % cerco....trovato per tentativi.....
        controllo=findstr(stringacontrollo,char(14));
        if controllo>0
            simbolostrano=1;
        else
            simbolostrano=0;
        end
        % se il primo valore della stringa letta è una "R" e non c'è
        % nessun simbolo strano,leggi l'ultimo
        if primo=='R' && simbolostrano==0
            stringa=z{j,1}(2:end);
            lung=length(stringa);
            ultimo=stringa(lung);
            % se la lughezza del dato dopo la R è pari a 3 spazi, il dato è
            % sempre corretto, esempio R2.3, R0.2, R3.5, ecc....
            % Alle volte mette lo zero alla fine alle volte no...BOH!!!!!
            if lung==3
                numero(h,1)=str2num(stringa);
                h=h+1;
            end
            % se la lughezza del dato dopo la R è pari a 4 spazi, il dato è
            % sempre corretto, esempio R2.35, R0.23, R3.50, ecc....
            % potrebbe esserci il caso di R2.3E...il programma di incazza!
            if lung==4
                numero(h,1)=str2num(stringa);
                h=h+1;
            end
            % se la linghezza del dato dopo la R è pari a 5 sono 3 le
            % configurazioni possibili: 
            % 1) 13.64 dato buono
            % 2) 3.44E devo eliminare la E (dato incerto, lo tengo)
            % 3) 5.66 devo eliminare il simbolo, dato buono
            % affrontiamo caso per caso
            if lung==5
                if stringa(3)=='.'
                    numero(h,1)=str2num(stringa);
                    h=h+1;
                else
                    stringaE=stringa(1:lung-1);
                    numero(h,1)=str2num(stringaE);
                    h=h+1;
                    E=E+1;
                end
            end
            if lung==6
                stringaE=stringa(1:lung-1);
                if stringaE=='99.99'
                    nodato=nodato+1;
                else
                    numero(h,1)=str2num(stringaE);
                    h=h+1;
                    E=E+1;
                end
            end
        end
        dati(k,1)=z(j,1);
        k=k+1;
        % se il primo valore non è una "R" butto il dato...
        end
    end
    tab_sonar(i,1)=i;
    % Media oraria (il sonar acquisisce una volta al secondo)
    % se nel file orario sono presenti più della metà di dati non validi
    % del tipo "R99.99.E" butto il file, quindi media zero!!!!!!!!!!!!!!
    if nodato>=1800
       tab_sonar(i,2)=0; 
    else
       tab_sonar(i,2)=12.33-(mean(numero(:,1),1));
    end
    % Varianza se nel file orario sono presenti più della metà di dati non validi
    % del tipo "R99.99.E" butto il file, quindi varianza zero!!!!!!!!!!!
    if nodato>=1800
       tab_sonar(i,3)=0; 
    else
       tab_sonar(i,3)=var(numero(:,1));
    end
    % Dati massimi totali all'ora utilizzabili per la media
    tab_sonar(i,4)=3600;
    % Dati reali utilizzati per la media
    % se nel file orario sono presenti più della metà di dati non validi
    % del tipo "R99.99.E" butto il file, quindi zero dati utili!!!!!!!!!
    if nodato>=1800
       tab_sonar(i,5)=0; 
    else
       tab_sonar(i,5)=size(numero,1);
    end
    % N° dati incerti utilizzati per la media
    tab_sonar(i,6)=E;
    % Percentuale di valori utilizzati rispetto ai 3600
    % che dovrebbero esserci per la media. Se nel file orario sono presenti 
    % più della metà di dati non validi del tipo "R99.99.E" butto il file,
    % quindi zero!!!!!!!!!
    if nodato>=1800
       tab_sonar(i,7)=0; 
    else
        tab_sonar(i,7)=tab_sonar(i,5)/tab_sonar(i,4)*100;
    end
    % Percentuale di valori incerti (rispetto ai valori utili)
    % utilizzati per il calcolo di media e varianza. Se nel file orario 
    % sono presenti più della metà di dati non validi del tipo "R99.99.E" 
    % butto il file, quindi zero!!!!!!!!!
    if nodato>=1800
       tab_sonar(i,8)=0; 
    else
        tab_sonar(i,8)=tab_sonar(i,6)/tab_sonar(i,5)*100;
    end
    timing=matrice_analisi_sonar(i,5)+matrice_analisi_sonar(i,6)/60+...
    matrice_analisi_sonar(i,7)/3600;
    tab_sonar(i,9)=timing;
    
    k=1;
    h=1;
    E=0;
    nodato=0;
    clear numero;    
end

for i=1:size(matrice_analisi_sonar,1)
    for j=1:16
        if j<8
            matrice_totale_sonar(i,j)=matrice_analisi_sonar(i,j);
        else
            matrice_totale_sonar(i,j)=tab_sonar(i,j-7);
        end
    end
end
% TELECAMERA MODENA
for i=1:size(mat_telmo_elab,1)
    matrice_analisi_telmo(i,1)=mat_telmo_elab(i,1);
    matrice_analisi_telmo(i,2)=mat_telmo_elab(i,2);
    matrice_analisi_telmo(i,3)=mat_telmo_elab(i,3);
    matrice_analisi_telmo(i,4)=mat_telmo_elab(i,4);
    matrice_analisi_telmo(i,5)=mat_telmo_elab(i,5);
    matrice_analisi_telmo(i,6)=mat_telmo_elab(i,6);
    matrice_analisi_telmo(i,7)=mat_telmo_elab(i,7);
    matrice_analisi_telmo(i,8)=mat_telmo_elab(i,5)+...
        mat_telmo_elab(i,6)/60+...
        mat_telmo_elab(i,7)/3600;  
end
% TELECAMERA MANTOVA
for i=1:size(mat_telma_elab,1)
    matrice_analisi_telma(i,1)=mat_telma_elab(i,1);
    matrice_analisi_telma(i,2)=mat_telma_elab(i,2);
    matrice_analisi_telma(i,3)=mat_telma_elab(i,3);
    matrice_analisi_telma(i,4)=mat_telma_elab(i,4);
    matrice_analisi_telma(i,5)=mat_telma_elab(i,5);
    matrice_analisi_telma(i,6)=mat_telma_elab(i,6);
    matrice_analisi_telma(i,7)=mat_telma_elab(i,7);
    matrice_analisi_telma(i,8)=mat_telma_elab(i,5)+...
        mat_telma_elab(i,6)/60+...
        mat_telma_elab(i,7)/3600;  
end


 
 
 
% messaggio finale ========================================================
inprogress='FATTO !!!!!!!!!!!';
set(handles.text12,'String',inprogress);
set(handles.text12,'ForegroundColor',[1 0 0]);
set(handles.pushbutton8,'BackgroundColor',[0 1 0]);
    
%==========================================================================
% CHECK DATI ==============================================================
%==========================================================================
% --- Executes on button press in pushbutton10.
function pushbutton10_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton10 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global matrice_totale_sonar;
global matrice_totale_ane;
global matrice_totale_idro;
global matrice_analisi_telma; 
global matrice_analisi_telmo;

global checktable;

for i=1:24
    % compilazione tabella colonna ANEMOMETRO =============================
    trovato_ane=0;
    for j=1:size(matrice_totale_ane,1)
        if matrice_totale_ane(j,8)>i-1
            if matrice_totale_ane(j,8)<=i
                checktable(i,1)=1;
                trovato_ane=1;
            end
        end
    end
    if trovato_ane==0
        checktable(i,1)=0;
    end
    % compilazione tabella colonna IDROMETRO ==============================
    trovato_idro=0;
    for j=1:size(matrice_totale_idro,1)
        if matrice_totale_idro(j,8)>i-1
            if matrice_totale_idro(j,8)<=i
                checktable(i,2)=1;
                trovato_idro=1;
            end
        end
    end
    if trovato_idro==0
        checktable(i,2)=0;
    end
    % compilazione tabella colonna SONAR ==================================
    trovato_sonar=0;
    for j=1:size(matrice_totale_sonar,1)
        if matrice_totale_sonar(j,16)>i-1
            if matrice_totale_sonar(j,16)<=i
                checktable(i,3)=1;
                trovato_sonar=1;
            end
        end
    end
    if trovato_sonar==0
        checktable(i,3)=0;
    end
    % compilazione tabella colonna TELMA ==================================
    trovato_telma=0;
    for j=1:size(matrice_analisi_telma,1)
        if matrice_analisi_telma(j,8)>i-1
            if matrice_analisi_telma(j,8)<=i
                checktable(i,4)=1;
                trovato_telma=1;
            end
        end
    end
    if trovato_telma==0
        checktable(i,4)=0;
    end
    % compilazione tabella colonna TELMO ==================================
    trovato_telmo=0;
    for j=1:size(matrice_analisi_telmo,1)
        if matrice_analisi_telmo(j,8)>i-1
            if matrice_analisi_telmo(j,8)<=i
                checktable(i,5)=1;
                trovato_telmo=1;
            end
        end
    end
    if trovato_telmo==0
        checktable(i,5)=0;
    end
end

set(handles.uitable2,'data',checktable);
set(handles.pushbutton10,'BackgroundColor',[0 1 0]);
%==========================================================================
%==========================================================================
%==========================================================================
%==========================================================================

%==========================================================================
% GRAFICI =================================================================
%==========================================================================
% --- Executes on button press in pushbutton9.
function pushbutton9_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton9 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global arrivo;

global tab_vento; global tab_sonar; global tab_idro;
global matrice_totale_idro;

global chkMA; global chkMO;

global mat_telmo_elab; global mat_telma_elab;

% PREPARAZIONE PER I GRAFICI DEL VENTO ====================================
% creazione delle linee per direzione vento (evidenziare le direzioni
% cardinali principali

clear e; clear ee; clear eee;

set(handles.pushbutton9,'BackgroundColor',[0 1 0]);


for i=1:24
    xdir(i,1)=i;
    y0(i,1)=0;     % da Nord  a Sud
    y90(i,1)=90;   % da Est   a Ovest
    y180(i,1)=180; % da Sud   a Nord
    y270(i,1)=270; % da Ovest a Est
    y360(i,1)=360; % da Nord  a Sud
end

% creazione vettori per grafico vento
x=tab_vento(:,7); %asse delle x
y1=tab_vento(:,2); % velocità del vento
y2=tab_vento(:,5); % folata
y3=tab_vento(:,3); % direzione velcoità vento
y4=tab_vento(:,6); % direzione folata vento

% identificazione del max delle ordinate per la velocità
maxfolata=max(y2)+5;

% Aquisizione delle dimensioni dello schermo
scrsz = get(0,'ScreenSize');

% posizionamento del primo grafico in alto a sinistra
figura1=figure('name','Velocità del vento e folata(max) ogni 10 minuti',...
    'numberTitle','off',...
    'Position',[10 scrsz(4)/2+30 scrsz(3)/2-50 scrsz(4)/2-120]);

% grafico con la velocità e max folata
plot(x,y1,'-r',...
    x,y2,'og',...
    'MarkerEdgeColor','k',...
    'MarkerFaceColor','g',...
    'MarkerSize',5),...
    axis([1 24 0 maxfolata]),...
    xlabel('tempo(ore)'),ylabel('velocità(m/s)'),...
    legend('vel media','max folata','location','Best'),...
    title('velocità media e folata ogni 10 min');
% attivazione della griglia
grid on;
% settaggio degli assi
set(gca,'XTick',0:1:24);

% posizionamento del secondo grafico in alto a destra
figura2=figure('name','Direzione del vento e della folata(max) ogni 10 minuti',...
    'numberTitle','off',...
    'Position',[10+scrsz(3)/2 scrsz(4)/2+30 scrsz(3)/2-50 scrsz(4)/2-120]);

% grafico con la direzione e direzione della folata
plot(x,y3,'-r',...
    x,y4,'og',...
    'MarkerEdgeColor','k',...
    'MarkerFaceColor','g',...
    'MarkerSize',5),...
    axis([1 24 0 360]),...
    xlabel('tempo(ore)'),ylabel('direzione(gradi)'),...
    legend('dir media','dir folata','location','Best'),...
    title('direzione media e folata ogni 10 min');
% attivazione della griglia
grid on;
% settaggio degli assi
set(gca,'XTick',0:1:24);
set(gca,'YTick',0:90:360);
% settaggio visualizzazione valori asse ordinate
set(gca,'YTickLabel',{'0','90','180','270','360'});
% mantengo il plot aperto per plottare altre informazioni
hold on;
% plotto le linee delle direzioni principali
plot(xdir,y0,'-r','LineWidth',2);
plot(xdir,y90,'-r','LineWidth',2);
plot(xdir,y180,'-r','LineWidth',2);
plot(xdir,y270,'-r','LineWidth',2);
plot(xdir,y360,'-r','LineWidth',2);
% inserisco etichette per direzione del vento
text(24,0,'  N\rightarrowS','HorizontalAlignment','left');
text(24,90,'  E\rightarrowW','HorizontalAlignment','left');
text(24,180,'  S\rightarrowN','HorizontalAlignment','left');
text(24,270,'  W\rightarrowE','HorizontalAlignment','left');
text(24,360,'  N\rightarrowS','HorizontalAlignment','left');
hold off;


% PREPARAZIONE PER IL GRAFICO IDROMETRO E SONAR ===========================
% creazione vettori per grafico idrometro e sonar
asseXsonar=tab_sonar(:,9); asseYsonar=tab_sonar(:,2);
asseXidro=matrice_totale_idro(:,8); asseYidro=tab_idro(:,1);
% definizione del massimo valore dell'asse delle Y
maxYsonar=max(asseYsonar);
maxYidro=max(asseYidro);
maxY=max(maxYsonar,maxYidro);
maxY=floor(maxY)+3;

% posizionamento del primo grafico in basso a sinistra
figura3=figure('name','Idrometro e sonar (quote orarie)',...
    'numberTitle','off',...
    'Position',[10 scrsz(4)/2-350 scrsz(3)/2-50 scrsz(4)/2-120]);

% grafico con valori in m slm dell'idrometro e del sonar
plot(asseXsonar,asseYsonar,'-k',...
    asseXidro,asseYidro,'-b',...
    'LineWidth',2),...
    axis([1 24 0 maxY]),...
    xlabel('tempo(ore)'),ylabel('quote(m slm)'),...
    legend('quota fondo','quota idrica','location','Best'),...
    title('quote del fondo del fiume e del tirante');

% attivazione della griglia
grid on;
% settaggio degli assi
set(gca,'XTick',0:1:24);
hold off;

% PREPARAZIONE PER VISUALIZZARE LE DUE TELECAMERE =========================
%==========================================================================
% apertura della finestra per la visualizzazione di una sola visuale delle 
% due a disposizione. A seconda della spunta o meno delle due checkbox
% cambia il titolo della finestra.....
if chkMO==1
        figura4=figure('name','Immagine telecamera MODENA (scelta utente)',...
            'numberTitle','off',...
            'Position',[10+scrsz(3)/2 scrsz(4)/2-350 scrsz(3)/2-50 scrsz(4)/2-120]);
else
        figura4=figure('name','Immagine telecamera MANTOVA (scelta utente)',...
            'numberTitle','off',...
            'Position',[10+scrsz(3)/2 scrsz(4)/2-350 scrsz(3)/2-50 scrsz(4)/2-120]);
end

cd(arrivo);
e=dir; k=1; b=1;
for i=3:size(e,1)
    trova_immagine=e(i).name;
    for j=1:size(mat_telmo_elab)
        data_imm=num2str(mat_telmo_elab(j,1));
        nome_imm=strcat('modena',data_imm,'.jpg');
        IMM_TF=strcmp(nome_imm,trova_immagine);
        if IMM_TF==1
            e_imm_mo(k,1)=e(i,1);
            k=k+1;
        end    
     end
     for j=1:size(mat_telma_elab)
        data_imm=num2str(mat_telma_elab(j,1));
        nome_imm=strcat('mantova',data_imm,'.jpg');
        IMM_TF=strcmp(nome_imm,trova_immagine);
        if IMM_TF==1
            e_imm_ma(b,1)=e(i,1);
            b=b+1;
         end    
     end
end

%==========================================================================
if chkMO==1 
    for i=1:size(e_imm_mo,1)
        figura=e_imm_mo(i).name;
        imshow(figura, 'InitialMagnification', 'Fit');
        pause(0.5);
    end
else
    for i=1:size(e_imm_ma,1)
        figura=e_imm_ma(i).name;
        imshow(figura, 'InitialMagnification', 'Fit');
        pause(0.5);
    end
end

chiusura=0;

while chiusura==0
% questdlg: non è possibile spostare la casella sullo schermo...
choice=questdlg('Cosa vuoi fare adesso?','Opzioni possibili',...
    'Rivedere lato Mantova','Rivedere lato Modena','Continuare','Continuare');

switch choice
    case 'Rivedere lato Mantova'
            for i=1:size(e_imm_ma,1)
                figura=e_imm_ma(i).name;
                imshow(figura, 'InitialMagnification', 'Fit');
                pause(0.5);
            end
            set(handles.checkbox1,'value',1);
            set(handles.checkbox2,'value',0);
    case 'Rivedere lato Modena'
            for i=1:size(e_imm_mo,1)
                figura=e_imm_mo(i).name;
                imshow(figura, 'InitialMagnification', 'Fit');
                pause(0.5);
            end
            set(handles.checkbox1,'value',0);
            set(handles.checkbox2,'value',1);
    case 'Continuare'
        close(figura1);
        close(figura2);
        close(figura3);
        close(figura4);
        chiusura=1;
end % end switch
end %end while


% --- Executes on button press in checkbox1.
function checkbox1_Callback(hObject, eventdata, handles)
% hObject    handle to checkbox1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of checkbox1
global chkMA; global chkMO;
chkMA=get(hObject,'Value');
if chkMA==1
    set(handles.checkbox2,'Value',0);
else
    set(handles.checkbox2,'Value',1);
end
chkMO=get(handles.checkbox2,'Value');


% --- Executes on button press in checkbox2.
function checkbox2_Callback(hObject, eventdata, handles)
% hObject    handle to checkbox2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of checkbox2
global chkMO; global chkMA;
chkMO=get(hObject,'Value');
if chkMO==1
    set(handles.checkbox1,'Value',0);
else
    set(handles.checkbox1,'Value',1);
end
chkMA=get(handles.checkbox1,'Value');



% BLOCCO (5): Assenza di detriti per tutta la "giornata" ==================
% --- Executes on button press in checkbox3.
function checkbox3_Callback(hObject, eventdata, handles)
% hObject    handle to checkbox3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of checkbox3
global casoC; global casoA; global casoB;
casoA=get(hObject,'Value');
if casoA==1
    set(handles.checkbox4,'Value',0);
    set(handles.checkbox5,'Value',0);
end
casoB=get(handles.checkbox4,'Value');
casoC=get(handles.checkbox5,'Value');

% BLOCCO (5): Presenza di detriti per tutta la "giornata" =================
% --- Executes on button press in checkbox4.
function checkbox4_Callback(hObject, eventdata, handles)
% hObject    handle to checkbox4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of checkbox4
global casoC; global casoA; global casoB;
casoB=get(hObject,'Value');
if casoB==1
    set(handles.checkbox3,'Value',0);
    set(handles.checkbox5,'Value',0);
end
casoA=get(handles.checkbox3,'Value');
casoC=get(handles.checkbox5,'Value');
% --- Executes on selection change in popupmenu1.
function popupmenu1_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = get(hObject,'String') returns popupmenu1 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu1
global Dalle;
Dalle=get(handles.popupmenu1,'Value')-1;

% --- Executes during object creation, after setting all properties.
function popupmenu1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on selection change in popupmenu2.
function popupmenu2_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = get(hObject,'String') returns popupmenu2 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu2
global Alle;
Alle=get(handles.popupmenu2,'Value');


% --- Executes during object creation, after setting all properties.
function popupmenu2_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popupmenu2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

% BLOCCO (5): Definizione del range di presenza detriti ===================
% --- Executes on button press in checkbox5.
function checkbox5_Callback(hObject, eventdata, handles)
% hObject    handle to checkbox5 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of checkbox5
global casoC; global casoA; global casoB;
casoC=get(hObject,'Value');
if casoC==1
    set(handles.checkbox3,'Value',0);
    set(handles.checkbox4,'Value',0);
end
casoA=get(handles.checkbox3,'Value');
casoB=get(handles.checkbox4,'Value');

% =========================================================================
% SET =====================================================================
% =========================================================================
% --- Executes on button press in pushbutton14.
function pushbutton14_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton14 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Acquisizione delle informazioni sui detriti settati dall'utente........

global matrice_analisi_telma; global matrice_analisi_telmo;
global matrice_totale_telma; global matrice_totale_telmo;
global casoA; global casoB; global casoC;
global Dalle; global Alle;

if casoA==1
    for i=1:size(matrice_analisi_telmo,1)
        for j=1:8
            matrice_totale_telmo(i,j)=matrice_analisi_telmo(i,j);
            matrice_totale_telmo(i,9)=0;
        end
    end
    for i=1:size(matrice_analisi_telma,1)
        for j=1:8
            matrice_totale_telma(i,j)=matrice_analisi_telma(i,j);
            matrice_totale_telma(i,9)=0;
        end
    end
end

if casoB==1
    for i=1:size(matrice_analisi_telmo,1)
        for j=1:8
            matrice_totale_telmo(i,j)=matrice_analisi_telmo(i,j);
            matrice_totale_telmo(i,9)=1;
        end
    end
    for i=1:size(matrice_analisi_telma,1)
        for j=1:8
            matrice_totale_telma(i,j)=matrice_analisi_telma(i,j);
            matrice_totale_telma(i,9)=1;
        end
    end
end

if casoC==1
    
    Dalle=get(handles.popupmenu1,'Value')-1;
    Alle=get(handles.popupmenu2,'Value');
    
    for i=1:size(matrice_analisi_telma,1)
        if matrice_analisi_telma(i,8)>Dalle
            if matrice_analisi_telma(i,8)<=Alle
                matrice_totale_telma(i,9)=1;
            else
                matrice_totale_telma(i,9)=0;
            end
        else
            matrice_totale_telma(i,9)=0;
        end
    end
    for i=1:size(matrice_analisi_telmo,1)
        if matrice_analisi_telmo(i,8)>Dalle
            if matrice_analisi_telmo(i,8)<=Alle
                matrice_totale_telmo(i,9)=1;
            else
                matrice_totale_telmo(i,9)=0;
            end
        else
            matrice_totale_telmo(i,9)=0;
        end
    end
    
    
end
set(handles.pushbutton14,'BackgroundColor',[0 1 0]);

%===================== BLOCCO (6) Ceoff. di sicurezza =====================
% CAMBIA CARTELLA FILE SETUP ==============================================
% =========================================================================
% --- Executes on button press in pushbutton21.
function pushbutton21_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton21 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global cod_setup; global setup;
cod_setup=uigetdir;
set(handles.text37,'String',cod_setup);
set(handles.text37,'ForegroundColor',[1 0 0]);
cd(cod_setup);
setup=textread('File Setup.txt','%s');

set(handles.pushbutton21,'BackgroundColor',[0 1 0]);
cd(arrivo);

%==========================================================================
%================================== CS ====================================
%==========================================================================
% --- Executes on button press in pushbutton22.
function pushbutton22_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton22 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global ck_setup; global cod_setup; global setup;
global mat_comb; global orack;

global mat_comb_pali; global mat_comb_ecc; global mat_comb_sigma;
global mat_comb_sicurezza; global mat_comb_sicurezza_max;

global tab_PP00;  % tabella peso prorpio
global tab_AQD0; % tabella spinta acqua no detriti
global tab_AQD1; % tabella spinta acqua si detriti
global tab_VT0;  % tabella vento (no traffico)
global tab_VT1A1; % tabella vento (traffico A1)
global tab_VT1A2; % tabella vento (traffico A2)
global tab_VT1A3; % tabella vento (traffico A3)
global tab_FR01;  % tabella frenata dei veicoli
global tab_FR02;  % tabella frenata dei veicoli
global tab_A110; % tabella carichi mobili A1
global tab_A120; % tabella carichi mobili A1
global tab_A210; % tabella carichi mobili A2
global tab_A220; % tabella carichi mobili A2
global tab_A311; % tabella carichi mobili A3
global tab_A312; % tabella carichi mobili A3
global tab_A321; % tabella carichi mobili A3
global tab_A322; % tabella carichi mobili A3
global tab_TMA;  % tabella telecamera mantova
global tab_TMO;  % tabella telecamera modena

while ck_setup==0
    cod_setup=uigetdir;
    nome_setup='File Setup.txt';
    ddd=dir;
    for i=1:size(ddd,1)
        ck_setup=strcmp(nome_setup,ddd(i).name);
        if ck_setup==1
            setup=textread('File Setup.txt','%s');
            set(handles.radiobutton2,'Value',1);
        end
    end
end
%richiamo variabili utili per i calcoli
global matrice_totale_telma; global matrice_totale_telmo;
global matrice_totale_sonar; global matrice_totale_ane;
global matrice_totale_idro; 
% =========================================================================
% generalita': con questo blocco si creano delle tabelle riassuntive, una
% per ogni forzante, nelle quali sono racchiuse tutte le sollecitazioni
% alla generica fila di pali (lato Mantova e Modena). Alcune di queste
% forzanti non hanno però direzione definita dal monitoraggio. Al fine di
% avere a disposizione tutte le possibili combinazioni si creeranno tante
% tabelle, una per ogni caso....
% Ad esempio per i carichi mobili alcune combinazioni possono essere
% realizzate posizionando i pesi verso monte e/o valle verso Mantova e/o
% Modena....
% =========================================================================
% Elenco delle tabelle/matrici
% tab_PP00   >> Tabella dei pesi propri (dipende da ds)
% tab_AQD0   >> Tabella spinta acqua (D=0)
% tab_AQD1   >> Tabella spinta acqua (D=1)
% tab_VT0    >> Tabella spinta vento (T=0)
% tab_VT1A1  >> Tabella spinta vento (T=1, A1)
% tab_VT1A2  >> Tabella spinta vento (T=1, A2)
% tab_VT1A3  >> Tabella spinta vento (T=1, A3)
% tab_FR01   >> Tabella frenata (1 MODENA>MANTOVA)
% tab_FR02   >> Tabella frenata (2 MANTOVA>MODENA)
% tab_A110   >> Tabella c. mobile A1 (1 MONTE>VALLE)
% tab_A120   >> Tabella c. mobile A1 (2 VALLE>MONTE)
% tab_A210   >> Tabella c. mobile A2 (1 MONTE>VALLE)
% tab_A220   >> Tabella c. mobile A2 (2 VALLE>MONTE)
% tab_A311   >> Tabella c. mobile A3 (1 MONTE>VALLE,1 MODENA>MANTOVA)
% tab_A312   >> Tabella c. mobile A3 (1 MONTE>VALLE,2 MANTOVA>MODENA)
% tab_A321   >> Tabella c. mobile A3 (2 VALLE>MONTE,1 MODENA>MANTOVA)
% tab_A322   >> Tabella c. mobile A3 (2 VALLE>MONTE,2 MANTOVA>MODENA)
% tab_TMA    >> Tabella telecamera Mantova
% tab_TMO    >> Tabella telecamera Modena

% =========================================================================
% CALCOLO PESO PROPRIO DELLA STRUTTURA ====================================
% =========================================================================
%k: eventuale affondamendo dell'incastro rispetto alla quota del fondo
parK=str2num(setup{22,1}); % da file txt
for i=1:24
    checkdato=0;
    tab_PP00(i,1)=i; % COLONNA 1: ore progressive (1-24)
    % COLONNA 2: quota del fondo (1000 se non ho il dato)
    for j=1:size(matrice_totale_sonar,1)
        if matrice_totale_sonar(j,16)>i-1
            if matrice_totale_sonar(j,16)<=i
                tab_PP00(i,2)=matrice_totale_sonar(j,9);
                checkdato=1;
            end
        end
    end
    if checkdato==0;
        tab_PP00(i,2)=1000;
    end
end
% eventuale interpolazione per mancanza quota fondo
% 1 CASO: manca il primo dato o i primi dati.......
uscitaciclo=0;
if tab_PP00(1,2)==1000
    for i=2:24
        if tab_PP00(i,2)~=1000
            if uscitaciclo==0;
                riferimento=i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=1:riferimento
        tab_PP00(i,2)=tab_PP00(riferimento,2);
    end
end
% 2 CASO: manca l'ultimo dato o gli ultimi dati 
uscitaciclo=0;
if tab_PP00(24,2)==1000
    for i=1:23
        if tab_PP00(24-i,2)~=1000
            if uscitaciclo==0;
                riferimento=24-i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=riferimento:24
        tab_PP00(i,2)=tab_PP00(riferimento,2);
    end
end
% 3 CASO: manca un solo dato o un blocco dati....
for i=2:23
    if tab_PP00(i,2)==1000
        contatore=0;
        cont=i;
        while tab_PP00(cont,2)==1000
                cont=cont+1;
                contatore=contatore+1;
        end
        primo=tab_PP00(i-1,2);
        ultimo=tab_PP00(i+contatore,2);
        delta=(ultimo-primo)/(contatore+1);
        for j=(cont-contatore):(cont+contatore-2)
            tab_PP00(j,2)=tab_PP00(j-1,2)+delta;
        end
    end
end
%acquisizione quota traverso inferiore per determinare la lunghezza dei
%pali fuori dal fondo
parTr=str2num(setup{24,1});
%determinazione del peso proprio della pila fino al traverso compreso
parPP=str2num(setup{183,1})+str2num(setup{185,1})+...
    str2num(setup{187,1})+str2num(setup{189,1});
parPmetro=str2num(setup{191,1});

for i=1:24
    % colonna 3 quota incastri (se k=0 uguale quota fondo)
    tab_PP00(i,3)=tab_PP00(i,2)-parK;
    tab_PP00(i,4)=parTr-tab_PP00(i,3);
    tab_PP00(i,5)=parPP+6*parPmetro*tab_PP00(i,4);
    tab_PP00(i,6)=str2num(setup{196,1})*str2num(setup{198,1})/...
        str2num(setup{16,1});
    % LATO MANTOVA ========================================================
    tab_PP00(i,7)=tab_PP00(i,5)/2+tab_PP00(i,6); % N
    tab_PP00(i,8)=0; % Tx
    tab_PP00(i,9)=0; % Ty
    tab_PP00(i,10)=0; %Mx
    % LATO MODENA =========================================================
    tab_PP00(i,11)=tab_PP00(i,5)/2-tab_PP00(i,6); % N
    tab_PP00(i,12)=0; % Tx
    tab_PP00(i,13)=0; % Ty
    tab_PP00(i,14)=0; %Mx 
end
% =========================================================================
% CALCOLO SPINTA DELL'ACQUA (CON E SENZA DETRITI) =========================
% =========================================================================
for i=1:24
    checkdato=0;
    % colonna 1 ore progressive (1-24)
    tab_AQD0(i,1)=i;
    tab_AQD1(i,1)=i;
    % colonna 2 quota livello Po (1000 se non ho il dato)
    for j=1:size(matrice_totale_idro,1)
        if matrice_totale_idro(j,8)>i-1
            if matrice_totale_idro(j,8)<=i
                tab_AQD0(i,2)=matrice_totale_idro(j,9);
                checkdato=1;
            end
        end
    end
    if checkdato==0;
        tab_AQD0(i,2)=1000;
    end
end
% eventuale interpolazione per mancanza quota livello Po
% 1 CASO: manca il primo dato o i primi dati.......
uscitaciclo=0;
if tab_AQD0(1,2)==1000
    for i=2:24
        if tab_AQD0(i,2)~=1000
            if uscitaciclo==0;
                riferimento=i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=1:riferimento
        tab_AQD0(i,2)=tab_AQD0(riferimento,2);
    end
end
% 2 CASO: manca l'ultimo dato o gli ultimi dati 
uscitaciclo=0;
if tab_AQD0(24,2)==1000
    for i=1:23
        if tab_AQD0(24-i,2)~=1000
            if uscitaciclo==0;
                riferimento=24-i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=riferimento:24
        tab_AQD0(i,2)=tab_AQD0(riferimento,2);
    end
end
% 3 CASO: manca un solo dato o un blocco dati....
for i=2:23
    if tab_AQD0(i,2)==1000
        contatore=0;
        cont=i;
        while tab_AQD0(cont,2)==1000
                cont=cont+1;
                contatore=contatore+1;
        end
        primo=tab_AQD0(i-1,2);
        ultimo=tab_AQD0(i+contatore,2);
        delta=(ultimo-primo)/(contatore+1);
        for j=(cont-contatore):(cont+contatore-2)
            tab_AQD0(j,2)=tab_AQD0(j-1,2)+delta;
        end
    end
end
% interpolato per tab_AQD0 si genera la colonna uguale per il caso tab_AQD1
for i=1:24
    tab_AQD1(i,2)=tab_AQD0(i,2);
    % COLONNA 3: quota di riferimento fondo
    tab_AQD0(i,3)=str2num(setup{26,1});
    tab_AQD1(i,3)=str2num(setup{26,1});
    % COLONNA 4: altezza (m) acqua pila 30
    tab_AQD0(i,4)=tab_AQD0(i,2)-tab_AQD0(i,3);
    tab_AQD1(i,4)=tab_AQD1(i,2)-tab_AQD1(i,3);
end
% COLONNA 5: determinazione della portata associata
for i=1:24
    if tab_AQD0(i,2)<=str2num(setup{85,1})
        tab_AQD0(i,5)=str2num(setup{87,1})*tab_AQD0(i,2)^2+...
            str2num(setup{89,1})*tab_AQD0(i,2)+...
            str2num(setup{91,1});
    else
        if tab_AQD0(i,2)<=str2num(setup{93,1})
        tab_AQD0(i,5)=str2num(setup{95,1})*tab_AQD0(i,2)^2+...
            str2num(setup{97,1})*tab_AQD0(i,2)+...
            str2num(setup{99,1});
        else
            tab_AQD0(i,5)=str2num(setup{101,1})*tab_AQD0(i,2)^2+...
            str2num(setup{103,1})*tab_AQD0(i,2)+...
            str2num(setup{105,1});
        end
    end
    tab_AQD1(i,5)=tab_AQD0(i,5);
end
% COLONNA 6: determinazione della velocità associata
for i=1:24
    tab_AQD0(i,6)=str2num(setup{78,1})*tab_AQD0(i,4)^3+...
    str2num(setup{80,1})*tab_AQD0(i,4)^2+...
    str2num(setup{82,1})*tab_AQD0(i,4);
    tab_AQD1(i,6)=tab_AQD0(i,6);
end
for i=1:24
    % COLONNA 7: densità acqua
    tab_AQD0(i,7)=str2num(setup{75,1});
    tab_AQD1(i,7)=str2num(setup{75,1});
    % COLONNA 8: coefficiente di Drag
    tab_AQD0(i,8)=str2num(setup{69,1});
    tab_AQD1(i,8)=str2num(setup{71,1});
    % COLONNA 9: Area della PILA 30
    tab_AQD0(i,9)=str2num(setup{14,1})*tab_AQD0(i,4);
    tab_AQD1(i,9)=str2num(setup{16,1})*tab_AQD1(i,4)*...
        str2num(setup{73,1});
end
for i=1:24
    % COLONNA 10: Spinta intera PILA 30
    tab_AQD0(i,10)=2*(1/2*tab_AQD0(i,8)*tab_AQD0(i,7)*tab_AQD0(i,9)*...
    tab_AQD0(i,6)^2)/1000;
    tab_AQD1(i,10)=(1/2*tab_AQD1(i,8)*tab_AQD1(i,7)*tab_AQD1(i,9)*...
    tab_AQD1(i,6)^2)/1000;
end
for i=1:24
    % SPINTA ACQUA SENZA ACCUMULO DETRITI A MONTE DELLA PILA ==============
    % LATO MANTOVA ========================================================
    tab_AQD0(i,11)=0; % N
    tab_AQD0(i,12)=0; % Tx
    tab_AQD0(i,13)=tab_AQD0(i,10)/2; % Ty
    tab_AQD0(i,14)=0; %Mx
    % LATO MODENA =========================================================
    tab_AQD0(i,15)=0; % N
    tab_AQD0(i,16)=0; % Tx
    tab_AQD0(i,17)=tab_AQD0(i,10)/2; % Ty
    tab_AQD0(i,18)=0; %Mx 
    % SPINTA ACQUA CON ACCUMULO DETRITI A MONTE DELLA PILA ================
    % LATO MANTOVA ========================================================
    tab_AQD1(i,11)=0; % N
    tab_AQD1(i,12)=0; % Tx
    tab_AQD1(i,13)=tab_AQD1(i,10)/2; % Ty
    tab_AQD1(i,14)=0; %Mx
    % LATO MODENA =========================================================
    tab_AQD1(i,15)=0; % N
    tab_AQD1(i,16)=0; % Tx
    tab_AQD1(i,17)=tab_AQD1(i,10)/2; % Ty
    tab_AQD1(i,18)=0; %Mx 
end
% =========================================================================
% CALCOLO SPINTA DEL VENTO (CON E SENZA TRAFFICO) =========================
% =========================================================================
for i=1:24
    checkdato=0;
    % colonna 1 ore progressive (1-24)
    tab_VT0(i,1)=i;
    % colonna 2 folata max oraria (1000 se non ho il dato)
    % colonna 3 dir. folata max   (1000 se non ho il dato)
    for j=1:size(matrice_totale_ane,1)
        if matrice_totale_ane(j,8)>i-1
            if matrice_totale_ane(j,8)<=i
                tab_VT0(i,2)=matrice_totale_ane(j,13);
                tab_VT0(i,3)=matrice_totale_ane(j,14);
                checkdato=1;
            end
        end
    end
    if checkdato==0;
        tab_VT0(i,2)=1000;
        tab_VT0(i,3)=1000;
    end
end
% eventuale interpolazione per mancanza dato vento
% 1 CASO: manca il primo dato o i primi dati.......
uscitaciclo=0;
if tab_VT0(1,2)==1000
    for i=2:24
        if tab_VT0(i,2)~=1000
            if uscitaciclo==0;
                riferimento=i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=1:riferimento
        tab_VT0(i,2)=tab_VT0(riferimento,2);
        tab_VT0(i,3)=tab_VT0(riferimento,3);
    end
end
% 2 CASO: manca l'ultimo dato o gli ultimi dati 
uscitaciclo=0;
if tab_VT0(24,2)==1000
    for i=1:23
        if tab_VT0(24-i,2)~=1000
            if uscitaciclo==0;
                riferimento=24-i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=riferimento:24
        tab_VT0(i,2)=tab_VT0(riferimento,2);
        tab_VT0(i,3)=tab_VT0(riferimento,3);
    end
end
% 3 CASO: manca un solo dato o un blocco dati....
for i=2:23
    if tab_VT0(i,2)==1000
        contatore=0;
        cont=i;
        while tab_VT0(cont,2)==1000
                cont=cont+1;
                contatore=contatore+1;
        end
        primoV=tab_VT0(i-1,2);
        primoD=tab_VT0(i-1,3);
        ultimoV=tab_VT0(i+contatore,2);
        ultimoD=tab_VT0(i+contatore,3);
        deltaV=(ultimoV-primoV)/(contatore+1);
        deltaD=(ultimoD-primoD)/(contatore+1);
        for j=(cont-contatore):(cont+contatore-2)
            tab_VT0(j,2)=tab_VT0(j-1,2)+deltaV;
            tab_VT0(j,3)=tab_VT0(j-1,3)+deltaD;
        end
    end
end
for i=1:24
    % COLONNA 4: rotazione del ponte rispetto al N (antiorario)
    tab_VT0(i,4)=str2num(setup{114,1});
    % COLONNA 5: velocità normale al ponte
    % COLONNA 6: 1 direzione W>E (come acqua), -1 contrario
    if tab_VT0(i,3)<=174
        tab_VT0(i,5)=tab_VT0(i,2)*(sind(tab_VT0(i,3)+ tab_VT0(i,4)));
        tab_VT0(i,6)=1;
    else
        tab_VT0(i,5)=-(tab_VT0(i,2)*(sind(tab_VT0(i,3)+ tab_VT0(i,4))));
        tab_VT0(i,6)=-1;
    end
end
for i=1:24
    % COLONNA 7: densità aria
    % COLONNA 8: coefficiente di Drag
    tab_VT0(i,7)=str2num(setup{112,1});
    tab_VT0(i,8)=str2num(setup{110,1});
end
% le colonne da 1 a 8 sono identiche a prescidere dal traffico, quindi
% genero anche le altre tabelle
for i=1:24
    for j=1:8
        tab_VT1A1(i,j)=tab_VT0(i,j);
        tab_VT1A2(i,j)=tab_VT0(i,j);
        tab_VT1A3(i,j)=tab_VT0(i,j);
    end
end
% COLONNA 9: area di riferimento
for i=1:24
    % A impalcato, traffico = 0
    tab_VT0(i,9)=str2num(setup{123,1});
    % A traffico coeff. = 1
    tab_VT1A1(i,9)=str2num(setup{126,1})*str2num(setup{128,1});
    % A traffico coeff. = 1
    tab_VT1A2(i,9)=str2num(setup{126,1})*str2num(setup{128,1});
    % A traffico coeff. = 0.5
    tab_VT1A3(i,9)=str2num(setup{126,1})*str2num(setup{130,1});
end
% COLONNA 10: Spinta vento PILA 30
for i=1:24
    tab_VT0(i,10)=(1/2*tab_VT0(i,7)*tab_VT0(i,8)*tab_VT0(i,9)*tab_VT0(i,5)^2)/1000;
    tab_VT1A1(i,10)=(1/2*tab_VT1A1(i,7)*tab_VT1A1(i,8)*tab_VT1A1(i,9)*tab_VT1A1(i,5)^2)/1000;
    tab_VT1A2(i,10)=(1/2*tab_VT1A2(i,7)*tab_VT1A2(i,8)*tab_VT1A2(i,9)*tab_VT1A2(i,5)^2)/1000;
    tab_VT1A3(i,10)=(1/2*tab_VT1A3(i,7)*tab_VT1A3(i,8)*tab_VT1A3(i,9)*tab_VT1A3(i,5)^2)/1000;
end
% COLONNA 11: Componente asimmetrica (solo per S impalcato)
% COLONNA 12: Momento associato alla spinta
for i=1:24
    tab_VT0(i,11)=tab_VT0(i,10)*str2num(setup{116,1})/str2num(setup{16,1});
    tab_VT1A1(i,11)=0;
    tab_VT1A2(i,11)=0;
    tab_VT1A3(i,11)=0;
    
    tab_VT0(i,12)=tab_VT0(i,10)*str2num(setup{118,1});
    tab_VT1A1(i,12)=tab_VT1A1(i,10)*str2num(setup{120,1});
    tab_VT1A2(i,12)=tab_VT1A2(i,10)*str2num(setup{120,1});
    tab_VT1A3(i,12)=tab_VT1A3(i,10)*str2num(setup{120,1});
end
% FORZANTI VENTO SULLE DUE FILE DI PALI====================================
for i=1:24
    % LATO MANTOVA ========================================================
    % no traffico
    tab_VT0(i,13)=0; % N
    tab_VT0(i,14)=0; % Tx
    tab_VT0(i,15)=tab_VT0(i,6)*(tab_VT0(i,10)/2+tab_VT0(i,11)); % Ty + asimmetrico
    tab_VT0(i,16)=tab_VT0(i,6)*tab_VT0(i,12)/2; % Mx
    % combinaziona A1
    tab_VT1A1(i,13)=0; % N
    tab_VT1A1(i,14)=0; % Tx
    tab_VT1A1(i,15)=tab_VT1A1(i,6)*tab_VT1A1(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A1(i,16)=tab_VT1A1(i,6)*tab_VT1A1(i,12)/2; % Mx
    % combinaziona A2
    tab_VT1A2(i,13)=0; % N
    tab_VT1A2(i,14)=0; % Tx
    tab_VT1A2(i,15)=tab_VT1A2(i,6)*tab_VT1A2(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A2(i,16)=tab_VT1A2(i,6)*tab_VT1A2(i,12)/2; % Mx
    % combinaziona A3
    tab_VT1A3(i,13)=0; % N
    tab_VT1A3(i,14)=0; % Tx
    tab_VT1A3(i,15)=tab_VT1A3(i,6)*tab_VT1A3(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A3(i,16)=tab_VT1A3(i,6)*tab_VT1A3(i,12)/2; % Mx
    % LATO MODENA =========================================================
    % no traffico
    tab_VT0(i,17)=0; % N
    tab_VT0(i,18)=0; % Tx
    tab_VT0(i,19)=tab_VT0(i,6)*(tab_VT0(i,10)/2-tab_VT0(i,11)); % Ty + asimmetrico
    tab_VT0(i,20)=tab_VT0(i,6)*tab_VT0(i,12)/2; % Mx
    % combinaziona A1
    tab_VT1A1(i,17)=0; % N
    tab_VT1A1(i,18)=0; % Tx
    tab_VT1A1(i,19)=tab_VT1A1(i,6)*tab_VT1A1(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A1(i,20)=tab_VT1A1(i,6)*tab_VT1A1(i,12)/2; % Mx
    % combinaziona A2
    tab_VT1A2(i,17)=0; % N
    tab_VT1A2(i,18)=0; % Tx
    tab_VT1A2(i,19)=tab_VT1A2(i,6)*tab_VT1A2(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A2(i,20)=tab_VT1A2(i,6)*tab_VT1A2(i,12)/2; % Mx
    % combinaziona A3
    tab_VT1A3(i,17)=0; % N
    tab_VT1A3(i,18)=0; % Tx
    tab_VT1A3(i,19)=tab_VT1A3(i,6)*tab_VT1A3(i,10)/2; % Ty (no asimmetrico)
    tab_VT1A3(i,20)=tab_VT1A3(i,6)*tab_VT1A3(i,12)/2; % Mx
end
% =========================================================================
% CALCOLO DELLA FRENATA DEI VEICOLI =======================================
% =========================================================================
for i=1:24 % direzione frenata MODENA>MANTOVA
    tab_FR01(i,1)=i; % COLONNA 1: orario (1:24)
    tab_FR01(i,2)=str2num(setup{137,1}); % COLONNA 2: Forza Ty....
    tab_FR01(i,3)=tab_FR01(i,2)*str2num(setup{135,1}); % COLONNA 3: Momento
    tab_FR01(i,4)=1; % COLONNA 4: direzizone frenata 1: MODENA>MANTOVA
    % LATO MANTOVA ========================================================
    tab_FR01(i,5)=tab_FR01(i,4)*(tab_FR01(i,3)/str2num(setup{16,1})); % N
    tab_FR01(i,6)=tab_FR01(i,4)*tab_FR01(i,2)/2; % Tx
    tab_FR01(i,7)=0; % Ty
    tab_FR01(i,8)=0; % Mx
    % LATO MODENA =========================================================
    tab_FR01(i,9)=-tab_FR01(i,4)*(tab_FR01(i,3)/str2num(setup{16,1})); % N
    tab_FR01(i,10)=tab_FR01(i,4)*tab_FR01(i,2)/2; % Tx
    tab_FR01(i,11)=0; % Ty
    tab_FR01(i,12)=0; % Mx
end
for i=1:24 % direzion frenata MANTOVA>MODENA
    tab_FR02(i,1)=i; % COLONNA 1: orario (1:24)
    tab_FR02(i,2)=str2num(setup{137,1}); % COLONNA 2: Forza Ty....
    tab_FR02(i,3)=tab_FR02(i,2)*str2num(setup{135,1}); % COLONNA 3: Momento
    tab_FR02(i,4)=-1; % COLONNA 4: direzizone frenata -1: MANTOVA>MODENA
    % LATO MANTOVA ========================================================
    tab_FR02(i,5)=tab_FR02(i,4)*(tab_FR02(i,3)/str2num(setup{16,1})); % N
    tab_FR02(i,6)=tab_FR02(i,4)*tab_FR02(i,2)/2; % Tx
    tab_FR02(i,7)=0; % Ty
    tab_FR02(i,8)=0; % Mx
    % LATO MODENA =========================================================
    tab_FR02(i,9)=-tab_FR02(i,4)*(tab_FR02(i,3)/str2num(setup{16,1})); % N
    tab_FR02(i,10)=tab_FR02(i,4)*tab_FR02(i,2)/2; % Tx
    tab_FR02(i,11)=0; % Ty
    tab_FR02(i,12)=0; % Mx
end
% =========================================================================
% CALCOLO DEI CARICHI MOBILI ==============================================
% =========================================================================

% A110 ====================================================================
% =========================================================================
% A1 trasversale  : rotazione oraria, da monte a valle
% A1 longitudinale: carichi su tutta la carreggiata, no gdl 
for i=1:24 % A1 ===========================================================
    tab_A110(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A110(i,2)=str2num(setup{142,1}); % COLONNA 2: N
    tab_A110(i,3)=str2num(setup{144,1}); % COLONNA 3: Mxx
    tab_A110(i,4)=str2num(setup{146,1}); % COLONNA 4: Myy
    tab_A110(i,5)=1; % COLONNA 5: direzione trasversale
    tab_A110(i,6)=0; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % dir. longitudinale:  0: Myy ruota in senso orario (no gdl)
    % LATO MANTOVA ========================================================
    tab_A110(i,7)=tab_A110(i,2)/2+tab_A110(i,4)/str2num(setup{16,1}); % N
    tab_A110(i,8)=0; % Tx
    tab_A110(i,9)=0; % Ty
    tab_A110(i,10)=tab_A110(i,5)*tab_A110(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A110(i,11)=tab_A110(i,2)/2-tab_A110(i,4)/str2num(setup{16,1}); % N
    tab_A110(i,12)=0; % Tx
    tab_A110(i,13)=0; % Ty
    tab_A110(i,14)=tab_A110(i,5)*tab_A110(i,3)/2; % Mx
end %======================================================================

% A120 ====================================================================
% =========================================================================
% A1 trasversale  : rotazione antioraria, da valle a monte
% A1 longitudinale: carichi su tutta la carreggiata, no gdl 
for i=1:24 % A1 ===========================================================
    tab_A120(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A120(i,2)=str2num(setup{142,1}); % COLONNA 2: N
    tab_A120(i,3)=str2num(setup{144,1}); % COLONNA 3: Mxx
    tab_A120(i,4)=str2num(setup{146,1}); % COLONNA 4: Myy
    tab_A120(i,5)=-1; % COLONNA 5: direzione trasversale
    tab_A120(i,6)=0; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % dir. longitudinale:  0: Myy ruota in senso orario (no gdl)
    % LATO MANTOVA ========================================================
    tab_A120(i,7)=tab_A120(i,2)/2+tab_A120(i,4)/str2num(setup{16,1}); % N
    tab_A120(i,8)=0; % Tx
    tab_A120(i,9)=0; % Ty
    tab_A120(i,10)=tab_A120(i,5)*tab_A120(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A120(i,11)=tab_A120(i,2)/2-tab_A120(i,4)/str2num(setup{16,1}); % N
    tab_A120(i,12)=0; % Tx
    tab_A120(i,13)=0; % Ty
    tab_A120(i,14)=tab_A120(i,5)*tab_A120(i,3)/2; % Mx
end %======================================================================

% A210 ====================================================================
% =========================================================================
% A2 trasversale  : rotazione oraria, da monte a valle
% A2 longitudinale: carichi su tutta la carreggiata, no gdl 
for i=1:24 % A2 ===========================================================
    tab_A210(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A210(i,2)=str2num(setup{151,1}); % COLONNA 2: N
    tab_A210(i,3)=str2num(setup{153,1}); % COLONNA 3: Mxx
    tab_A210(i,4)=str2num(setup{155,1}); % COLONNA 4: Myy
    tab_A210(i,5)=1; % COLONNA 5: direzione trasversale
    tab_A210(i,6)=0; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  0: Myy ruota in senso orario (no gdl)
    % LATO MANTOVA ========================================================
    tab_A210(i,7)=tab_A210(i,2)/2+tab_A210(i,4)/str2num(setup{16,1}); % N
    tab_A210(i,8)=0; % Tx
    tab_A210(i,9)=0; % Ty
    tab_A210(i,10)=tab_A210(i,5)*tab_A210(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A210(i,11)=tab_A210(i,2)/2-tab_A210(i,4)/str2num(setup{16,1}); % N
    tab_A210(i,12)=0; % Tx
    tab_A210(i,13)=0; % Ty
    tab_A210(i,14)=tab_A210(i,5)*tab_A210(i,3)/2; % Mx
end %======================================================================

% A220 ====================================================================
% =========================================================================
% A2 trasversale  : rotazione antioraria, da valle a monte
% A2 longitudinale: carichi su tutta la carreggiata, no gdl 
for i=1:24 % A2 ===========================================================
    tab_A220(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A220(i,2)=str2num(setup{151,1}); % COLONNA 2: N
    tab_A220(i,3)=str2num(setup{153,1}); % COLONNA 3: Mxx
    tab_A220(i,4)=str2num(setup{155,1}); % COLONNA 4: Myy
    tab_A220(i,5)=-1; % COLONNA 5: direzione trasversale
    tab_A220(i,6)=0; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  0: Myy ruota in senso orario (no gdl)
    % LATO MANTOVA ========================================================
    tab_A220(i,7)=tab_A220(i,2)/2+tab_A220(i,4)/str2num(setup{16,1}); % N
    tab_A220(i,8)=0; % Tx
    tab_A220(i,9)=0; % Ty
    tab_A220(i,10)=tab_A220(i,5)*tab_A220(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A220(i,11)=tab_A220(i,2)/2-tab_A220(i,4)/str2num(setup{16,1}); % N
    tab_A220(i,12)=0; % Tx
    tab_A220(i,13)=0; % Ty
    tab_A220(i,14)=tab_A220(i,5)*tab_A220(i,3)/2; % Mx
end %======================================================================

% A311 ====================================================================
% =========================================================================
% A3 trasversale  : rotazione oraria, da monte a valle
% A3 longitudinale: rotazione oraria, da modena a mantova 
for i=1:24 % A3 ===========================================================
    tab_A311(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A311(i,2)=str2num(setup{160,1}); % COLONNA 2: N
    tab_A311(i,3)=str2num(setup{162,1}); % COLONNA 3: Mxx
    tab_A311(i,4)=str2num(setup{164,1}); % COLONNA 4: Myy
    tab_A311(i,5)=1; % COLONNA 5: direzione trasversale
    tab_A311(i,6)=1; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % LATO MANTOVA ========================================================
    tab_A311(i,7)=tab_A311(i,2)/2+tab_A311(i,6)*tab_A311(i,4)/str2num(setup{16,1}); % N
    tab_A311(i,8)=0; % Tx
    tab_A311(i,9)=0; % Ty
    tab_A311(i,10)=tab_A311(i,5)*tab_A311(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A311(i,11)=tab_A311(i,2)/2-tab_A311(i,6)*tab_A311(i,4)/str2num(setup{16,1}); % N
    tab_A311(i,12)=0; % Tx
    tab_A311(i,13)=0; % Ty
    tab_A311(i,14)=tab_A311(i,5)*tab_A311(i,3)/2; % Mx
end %======================================================================

% A312 ====================================================================
% =========================================================================
% A3 trasversale  : rotazione oraria, da monte a valle
% A3 longitudinale: rotazione antioraria, da mantova a modena
for i=1:24 % A3 ===========================================================
    tab_A312(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A312(i,2)=str2num(setup{160,1}); % COLONNA 2: N
    tab_A312(i,3)=str2num(setup{162,1}); % COLONNA 3: Mxx
    tab_A312(i,4)=str2num(setup{164,1}); % COLONNA 4: Myy
    tab_A312(i,5)=1; % COLONNA 5: direzione trasversale
    tab_A312(i,6)=-1; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % LATO MANTOVA ========================================================
    tab_A312(i,7)=tab_A312(i,2)/2+tab_A312(i,6)*tab_A312(i,4)/str2num(setup{16,1}); % N
    tab_A312(i,8)=0; % Tx
    tab_A312(i,9)=0; % Ty
    tab_A312(i,10)=tab_A311(i,5)*tab_A311(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A312(i,11)=tab_A312(i,2)/2-tab_A312(i,6)*tab_A312(i,4)/str2num(setup{16,1}); % N
    tab_A312(i,12)=0; % Tx
    tab_A312(i,13)=0; % Ty
    tab_A312(i,14)=tab_A312(i,5)*tab_A312(i,3)/2; % Mx
end %======================================================================

% A321 ====================================================================
% =========================================================================
% A3 trasversale  : rotazione antioraria, da valle a monte
% A3 longitudinale: rotazione oraria, da modena a mantova
for i=1:24 % A3 ===========================================================
    tab_A321(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A321(i,2)=str2num(setup{160,1}); % COLONNA 2: N
    tab_A321(i,3)=str2num(setup{162,1}); % COLONNA 3: Mxx
    tab_A321(i,4)=str2num(setup{164,1}); % COLONNA 4: Myy
    tab_A321(i,5)=-1; % COLONNA 5: direzione trasversale
    tab_A321(i,6)=1; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % LATO MANTOVA ========================================================
    tab_A321(i,7)=tab_A321(i,2)/2+tab_A321(i,6)*tab_A321(i,4)/str2num(setup{16,1}); % N
    tab_A321(i,8)=0; % Tx
    tab_A321(i,9)=0; % Ty
    tab_A321(i,10)=tab_A321(i,5)*tab_A321(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A321(i,11)=tab_A321(i,2)/2-tab_A321(i,6)*tab_A321(i,4)/str2num(setup{16,1}); % N
    tab_A321(i,12)=0; % Tx
    tab_A321(i,13)=0; % Ty
    tab_A321(i,14)=tab_A321(i,5)*tab_A321(i,3)/2; % Mx
end %======================================================================

% A322 ====================================================================
% =========================================================================
% A3 trasversale  : rotazione antioraria, da valle a monte
% A3 longitudinale: rotazione antioraria, da mantova a modena
for i=1:24 % A3 ===========================================================
    tab_A322(i,1)=i; % COLONNA 1: orario (1:24)
    tab_A322(i,2)=str2num(setup{160,1}); % COLONNA 2: N
    tab_A322(i,3)=str2num(setup{162,1}); % COLONNA 3: Mxx
    tab_A322(i,4)=str2num(setup{164,1}); % COLONNA 4: Myy
    tab_A322(i,5)=-1; % COLONNA 5: direzione trasversale
    tab_A322(i,6)=-1; % COLONNA 6: direzione longitudinale
    % dir. trasversale:    1: Mxx ruota in senso orario (monte-valle)
    % dir. trasversale:   -1: Mxx ruota in senso antiorario
    % dir. longitudinale:  1: Myy ruota in senso orario (modena-mantova)
    % dir. longitudinale: -1: Myy ruota in senso antiorario
    % LATO MANTOVA ========================================================
    tab_A322(i,7)=tab_A322(i,2)/2+tab_A322(i,6)*tab_A322(i,4)/str2num(setup{16,1}); % N
    tab_A322(i,8)=0; % Tx
    tab_A322(i,9)=0; % Ty
    tab_A322(i,10)=tab_A322(i,5)*tab_A322(i,3)/2; % Mx
    % LATO MODENA =========================================================
    tab_A322(i,11)=tab_A322(i,2)/2-tab_A322(i,6)*tab_A322(i,4)/str2num(setup{16,1}); % N
    tab_A322(i,12)=0; % Tx
    tab_A322(i,13)=0; % Ty
    tab_A322(i,14)=tab_A322(i,5)*tab_A322(i,3)/2; % Mx
end 
% =========================================================================
% TELECAMERA MANTOVA E MODENA =============================================
% =========================================================================
for i=1:24
    checkdato=0;
    tab_TMA(i,1)=i; % COLONNA 1: ore progressive
    % COLONNA 2: presenza(1) assenza(0) di detriti (1000 se non ho il dato)
    for j=1:size(matrice_totale_telma,1)
        if matrice_totale_telma(j,8)>i-1
            if matrice_totale_telma(j,8)<=i
                tab_TMA(i,2)=matrice_totale_telma(j,9);
                checkdato=1;
            end
        end
    end
    if checkdato==0;
        tab_TMA(i,2)=1000;
    end
end
% eventuale interpolazione per mancanza dato presenza/assenza detriti
% 1 CASO: manca il primo dato o i primi dati.......
uscitaciclo=0;
if tab_TMA(1,2)==1000
    for i=2:24
        if tab_TMA(i,2)~=1000
            if uscitaciclo==0;
                riferimento=i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=1:riferimento
        tab_TMA(i,2)=tab_TMA(riferimento,2);
    end
end 
% 2 CASO: manca l'ultimo dato o gli ultimi dati 
uscitaciclo=0;
if tab_TMA(24,2)==1000
    for i=1:23
        if tab_TMA(24-i,2)~=1000
            if uscitaciclo==0;
                riferimento=24-i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=riferimento:24
        tab_TMA(i,2)=tab_TMA(riferimento,2);
    end
end
% 3 CASO: manca un solo dato o un blocco dati....
for i=2:23
    if tab_TMA(i,2)==1000
        contatore=0;
        cont=i;
        while tab_TMA(cont,2)==1000
                cont=cont+1;
                contatore=contatore+1;
        end
        primo=tab_TMA(i-1,2);
        for j=(cont-contatore):(cont+contatore-3)
            tab_TMA(j,2)=primo;
        end
    end
end
% telecamera MODENA
for i=1:24
    checkdato=0;
    tab_TMO(i,1)=i; % COLONNA 1: ore progressive
    % COLONNA 2: presenza(1) assenza(0) di detriti (1000 se non ho il dato)
    for j=1:size(matrice_totale_telmo,1)
        if matrice_totale_telmo(j,8)>i-1
            if matrice_totale_telmo(j,8)<=i
                tab_TMO(i,2)=matrice_totale_telmo(j,9);
                checkdato=1;
            end
        end
    end
    if checkdato==0;
        tab_TMO(i,2)=1000;
    end
end
% eventuale interpolazione per mancanza dato presenza/assenza detriti
% 1 CASO: manca il primo dato o i primi dati.......
uscitaciclo=0;
if tab_TMO(1,2)==1000
    for i=2:24
        if tab_TMO(i,2)~=1000
            if uscitaciclo==0;
                riferimento=i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=1:riferimento
        tab_TMO(i,2)=tab_TMO(riferimento,2);
    end
end 
% 2 CASO: manca l'ultimo dato o gli ultimi dati 
uscitaciclo=0;
if tab_TMO(24,2)==1000
    for i=1:23
        if tab_TMO(24-i,2)~=1000
            if uscitaciclo==0;
                riferimento=24-i;
                uscitaciclo=1;
            end
        end
    end
end
if uscitaciclo==1
    for i=riferimento:24
        tab_TMO(i,2)=tab_TMO(riferimento,2);
    end
end
% 3 CASO: manca un solo dato o un blocco dati....
for i=2:23
    if tab_TMO(i,2)==1000
        contatore=0;
        cont=i;
        while tab_TMO(cont,2)==1000
                cont=cont+1;
                contatore=contatore+1;
        end
        primo=tab_TMO(i-1,2);
        for j=(cont-contatore):(cont+contatore-3)
            tab_TMO(j,2)=primo;
        end
    end
end
pause(0.5);
set(handles.radiobutton3,'Value',1);
pause(0.5);

% http://www.mathworks.it/it/help/matlab/creating_guis/...
% ...gui-that-displays-and-graphs-tabular-data.html

% =========================================================================
% COMBINAZIONI DI CARICO ==================================================
% =========================================================================
% =========================================================================
% Osservazioni: anche se il caso acqua ha due tabelle esse non
% costituiscono due casi in quanto la presenza/assenza dei detriti è
% definita dalle immagini, quindi a seconda del caso (noto) si
% utilizzaranno i dati dell'una piuttosto che dell'altra
% =========================================================================
% combinazioni possibili da analizzare
% 
global comb_01; %01)% PP AQ VT0

global comb_02; %02)% PP AQ VT1 VT1A1 A110 FR01
global comb_03; %03)% PP AQ VT1 VT1A1 A110 FR02
global comb_04; %04)% PP AQ VT1 VT1A1 A120 FR01
global comb_05; %05)% PP AQ VT1 VT1A1 A120 FR02

global comb_06; %06)% PP AQ VT1 VT1A2 A210 FR01
global comb_07; %07)% PP AQ VT1 VT1A2 A210 FR02
global comb_08; %08)% PP AQ VT1 VT1A2 A220 FR01
global comb_09; %09)% PP AQ VT1 VT1A2 A220 FR02

global comb_10; %10)% PP AQ VT1 VT1A3 A311 FR01
global comb_11; %11)% PP AQ VT1 VT1A3 A311 FR02
global comb_12; %12)% PP AQ VT1 VT1A3 A312 FR01
global comb_13; %13)% PP AQ VT1 VT1A3 A312 FR02
global comb_14; %14)% PP AQ VT1 VT1A3 A321 FR01
global comb_15; %15)% PP AQ VT1 VT1A3 A321 FR02
global comb_16; %16)% PP AQ VT1 VT1A3 A322 FR01
global comb_17; %17)% PP AQ VT1 VT1A3 A322 FR02
% =========================================================================

% =========================================================================
%01)% PP AQ VT0 ===========================================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_01(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_01(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13);       %N
        comb_01(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14);       %Tx
        comb_01(i,4)=tab_PP00(i,9)+tab_VT0(i,15);                      %Ty
        comb_01(i,5)=tab_AQD0(i,13);                                   %Tya
        comb_01(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16);      %Mx
        % LATO MODENA =====================================================
        comb_01(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17);      %N
        comb_01(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18);      %Tx
        comb_01(i,9)=tab_PP00(i,13)+tab_VT0(i,19);                     %Ty
        comb_01(i,10)=tab_AQD0(i,17);                                  %Tya
        comb_01(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20);     %Mx
    else
        % LATO MANTOVA ====================================================
        comb_01(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13);       %N
        comb_01(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14);       %Tx
        comb_01(i,4)=tab_PP00(i,9)+tab_VT0(i,15);                      %Ty
        comb_01(i,5)=tab_AQD1(i,13);                                   %Tya
        comb_01(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16);      %Mx
        % LATO MODENA =====================================================
        comb_01(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17);      %N
        comb_01(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18);      %Tx
        comb_01(i,9)=tab_PP00(i,13)+tab_VT0(i,19);                     %Ty
        comb_01(i,10)=tab_AQD1(i,17);                                  %Tya
        comb_01(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20);     %Mx
    end
end
% =========================================================================
%02)% PP AQ VT1 VT1A1 A110 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_02(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_02(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A110(i,7)+tab_FR01(i,5);       %N
        comb_02(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A110(i,8)+tab_FR01(i,6);       %Tx
        comb_02(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A110(i,9)+tab_FR01(i,7);                      %Ty
        comb_02(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_02(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A110(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_02(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A110(i,11)+tab_FR01(i,9);     %N
        comb_02(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A110(i,12)+tab_FR01(i,10);    %Tx
        comb_02(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A110(i,13)+tab_FR01(i,11);                   %Ty
        comb_02(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_02(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A110(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_02(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A110(i,7)+tab_FR01(i,5);       %N
        comb_02(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A110(i,8)+tab_FR01(i,6);       %Tx
        comb_02(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A110(i,9)+tab_FR01(i,7);                      %Ty
        comb_02(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_02(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A110(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_02(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A110(i,11)+tab_FR01(i,9);     %N
        comb_02(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A110(i,12)+tab_FR01(i,10);    %Tx
        comb_02(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A110(i,13)+tab_FR01(i,11);                   %Ty
        comb_02(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_02(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A110(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%03)% PP AQ VT1 VT1A1 A110 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_03(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_03(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A110(i,7)+tab_FR02(i,5);       %N
        comb_03(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A110(i,8)+tab_FR02(i,6);       %Tx
        comb_03(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A110(i,9)+tab_FR02(i,7);                      %Ty
        comb_03(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_03(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A110(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_03(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A110(i,11)+tab_FR02(i,9);     %N
        comb_03(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A110(i,12)+tab_FR02(i,10);    %Tx
        comb_03(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A110(i,13)+tab_FR02(i,11);                   %Ty
        comb_03(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_03(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A110(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_03(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A110(i,7)+tab_FR02(i,5);       %N
        comb_03(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A110(i,8)+tab_FR02(i,6);       %Tx
        comb_03(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A110(i,9)+tab_FR02(i,7);                      %Ty
        comb_03(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_03(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A110(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_03(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A110(i,11)+tab_FR02(i,9);     %N
        comb_03(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A110(i,12)+tab_FR02(i,10);    %Tx
        comb_03(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A110(i,13)+tab_FR02(i,11);                   %Ty
        comb_03(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_03(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A110(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%04)% PP AQ VT1 VT1A1 A120 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_04(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_04(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A120(i,7)+tab_FR01(i,5);       %N
        comb_04(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A120(i,8)+tab_FR01(i,6);       %Tx
        comb_04(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A120(i,9)+tab_FR01(i,7);                      %Ty
        comb_04(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_04(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A120(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_04(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A120(i,11)+tab_FR01(i,9);     %N
        comb_04(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A120(i,12)+tab_FR01(i,10);    %Tx
        comb_04(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A120(i,13)+tab_FR01(i,11);                   %Ty
        comb_04(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_04(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A120(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_04(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A120(i,7)+tab_FR01(i,5);       %N
        comb_04(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A120(i,8)+tab_FR01(i,6);       %Tx
        comb_04(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A120(i,9)+tab_FR01(i,7);                      %Ty
        comb_04(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_04(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A120(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_04(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A120(i,11)+tab_FR01(i,9);     %N
        comb_04(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A120(i,12)+tab_FR01(i,10);    %Tx
        comb_04(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A120(i,13)+tab_FR01(i,11);                   %Ty
        comb_04(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_04(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A120(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%05)% PP AQ VT1 VT1A1 A120 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_05(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_05(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A120(i,7)+tab_FR02(i,5);       %N
        comb_05(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A120(i,8)+tab_FR02(i,6);       %Tx
        comb_05(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A120(i,9)+tab_FR02(i,7);                      %Ty
        comb_05(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_05(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A120(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_05(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A120(i,11)+tab_FR02(i,9);     %N
        comb_05(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A120(i,12)+tab_FR02(i,10);    %Tx
        comb_05(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A120(i,13)+tab_FR02(i,11);                   %Ty
        comb_05(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_05(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A120(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_05(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A1(i,13)+tab_A120(i,7)+tab_FR02(i,5);       %N
        comb_05(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A1(i,14)+tab_A120(i,8)+tab_FR02(i,6);       %Tx
        comb_05(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A1(i,15)+tab_A120(i,9)+tab_FR02(i,7);                      %Ty
        comb_05(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_05(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A1(i,16)+tab_A120(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_05(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A1(i,17)+tab_A120(i,11)+tab_FR02(i,9);     %N
        comb_05(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A1(i,18)+tab_A120(i,12)+tab_FR02(i,10);    %Tx
        comb_05(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A1(i,19)+tab_A120(i,13)+tab_FR02(i,11);                   %Ty
        comb_05(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_05(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A1(i,20)+tab_A120(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%06)% PP AQ VT1 VT1A2 A210 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_06(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_06(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A210(i,7)+tab_FR01(i,5);       %N
        comb_06(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A210(i,8)+tab_FR01(i,6);       %Tx
        comb_06(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A210(i,9)+tab_FR01(i,7);                      %Ty
        comb_06(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_06(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A210(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_06(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A210(i,11)+tab_FR01(i,9);     %N
        comb_06(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A210(i,12)+tab_FR01(i,10);    %Tx
        comb_06(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A210(i,13)+tab_FR01(i,11);                   %Ty
        comb_06(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_06(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A210(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_06(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A210(i,7)+tab_FR01(i,5);       %N
        comb_06(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A210(i,8)+tab_FR01(i,6);       %Tx
        comb_06(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A210(i,9)+tab_FR01(i,7);                      %Ty
        comb_06(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_06(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A210(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_06(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A210(i,11)+tab_FR01(i,9);     %N
        comb_06(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A210(i,12)+tab_FR01(i,10);    %Tx
        comb_06(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A210(i,13)+tab_FR01(i,11);                   %Ty
        comb_06(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_06(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A210(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%07)% PP AQ VT1 VT1A2 A210 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_07(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_07(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A210(i,7)+tab_FR02(i,5);       %N
        comb_07(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A210(i,8)+tab_FR02(i,6);       %Tx
        comb_07(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A210(i,9)+tab_FR02(i,7);                      %Ty
        comb_07(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_07(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A210(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_07(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A210(i,11)+tab_FR02(i,9);     %N
        comb_07(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A210(i,12)+tab_FR02(i,10);    %Tx
        comb_07(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A210(i,13)+tab_FR02(i,11);                   %Ty
        comb_07(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_07(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A210(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_07(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A210(i,7)+tab_FR02(i,5);       %N
        comb_07(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A210(i,8)+tab_FR02(i,6);       %Tx
        comb_07(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A210(i,9)+tab_FR02(i,7);                      %Ty
        comb_07(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_07(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A210(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_07(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A210(i,11)+tab_FR02(i,9);     %N
        comb_07(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A210(i,12)+tab_FR02(i,10);    %Tx
        comb_07(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A210(i,13)+tab_FR02(i,11);                   %Ty
        comb_07(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_07(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A210(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%08)% PP AQ VT1 VT1A2 A220 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_08(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_08(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A220(i,7)+tab_FR01(i,5);       %N
        comb_08(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A220(i,8)+tab_FR01(i,6);       %Tx
        comb_08(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A220(i,9)+tab_FR01(i,7);                      %Ty
        comb_08(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_08(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A220(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_08(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A220(i,11)+tab_FR01(i,9);     %N
        comb_08(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A220(i,12)+tab_FR01(i,10);    %Tx
        comb_08(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A220(i,13)+tab_FR01(i,11);                   %Ty
        comb_08(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_08(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A220(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_08(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A220(i,7)+tab_FR01(i,5);       %N
        comb_08(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A220(i,8)+tab_FR01(i,6);       %Tx
        comb_08(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A220(i,9)+tab_FR01(i,7);                      %Ty
        comb_08(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_08(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A220(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_08(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A220(i,11)+tab_FR01(i,9);     %N
        comb_08(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A220(i,12)+tab_FR01(i,10);    %Tx
        comb_08(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A220(i,13)+tab_FR01(i,11);                   %Ty
        comb_08(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_08(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A220(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%09)% PP AQ VT1 VT1A2 A220 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_09(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_09(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A220(i,7)+tab_FR02(i,5);       %N
        comb_09(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A220(i,8)+tab_FR02(i,6);       %Tx
        comb_09(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A220(i,9)+tab_FR02(i,7);                      %Ty
        comb_09(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_09(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A220(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_09(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A220(i,11)+tab_FR02(i,9);     %N
        comb_09(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A220(i,12)+tab_FR02(i,10);    %Tx
        comb_09(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A220(i,13)+tab_FR02(i,11);                   %Ty
        comb_09(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_09(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A220(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_09(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A2(i,13)+tab_A220(i,7)+tab_FR02(i,5);       %N
        comb_09(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A2(i,14)+tab_A220(i,8)+tab_FR02(i,6);       %Tx
        comb_09(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A2(i,15)+tab_A220(i,9)+tab_FR02(i,7);                      %Ty
        comb_09(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_09(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A2(i,16)+tab_A220(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_09(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A2(i,17)+tab_A220(i,11)+tab_FR02(i,9);     %N
        comb_09(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A2(i,18)+tab_A220(i,12)+tab_FR02(i,10);    %Tx
        comb_09(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A2(i,19)+tab_A220(i,13)+tab_FR02(i,11);                   %Ty
        comb_09(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_09(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A2(i,20)+tab_A220(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%10)% PP AQ VT1 VT1A3 A311 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_10(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_10(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A311(i,7)+tab_FR01(i,5);       %N
        comb_10(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A311(i,8)+tab_FR01(i,6);       %Tx
        comb_10(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A311(i,9)+tab_FR01(i,7);                      %Ty
        comb_10(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_10(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A311(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_10(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A311(i,11)+tab_FR01(i,9);     %N
        comb_10(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A311(i,12)+tab_FR01(i,10);    %Tx
        comb_10(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A311(i,13)+tab_FR01(i,11);                   %Ty
        comb_10(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_10(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A311(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_10(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A311(i,7)+tab_FR01(i,5);       %N
        comb_10(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A311(i,8)+tab_FR01(i,6);       %Tx
        comb_10(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A311(i,9)+tab_FR01(i,7);                      %Ty
        comb_10(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_10(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A311(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_10(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A311(i,11)+tab_FR01(i,9);     %N
        comb_10(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A311(i,12)+tab_FR01(i,10);    %Tx
        comb_10(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A311(i,13)+tab_FR01(i,11);                   %Ty
        comb_10(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_10(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A311(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%11)% PP AQ VT1 VT1A3 A311 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_11(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_11(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A311(i,7)+tab_FR02(i,5);       %N
        comb_11(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A311(i,8)+tab_FR02(i,6);       %Tx
        comb_11(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A311(i,9)+tab_FR02(i,7);                      %Ty
        comb_11(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_11(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A311(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_11(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A311(i,11)+tab_FR02(i,9);     %N
        comb_11(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A311(i,12)+tab_FR02(i,10);    %Tx
        comb_11(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A311(i,13)+tab_FR02(i,11);                   %Ty
        comb_11(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_11(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A311(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_11(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A311(i,7)+tab_FR02(i,5);       %N
        comb_11(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A311(i,8)+tab_FR02(i,6);       %Tx
        comb_11(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A311(i,9)+tab_FR02(i,7);                      %Ty
        comb_11(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_11(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A311(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_11(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A311(i,11)+tab_FR02(i,9);     %N
        comb_11(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A311(i,12)+tab_FR02(i,10);    %Tx
        comb_11(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A311(i,13)+tab_FR02(i,11);                   %Ty
        comb_11(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_11(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A311(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%12)% PP AQ VT1 VT1A3 A312 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_12(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_12(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A312(i,7)+tab_FR01(i,5);       %N
        comb_12(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A312(i,8)+tab_FR01(i,6);       %Tx
        comb_12(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A312(i,9)+tab_FR01(i,7);                      %Ty
        comb_12(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_12(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A312(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_12(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A312(i,11)+tab_FR01(i,9);     %N
        comb_12(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A312(i,12)+tab_FR01(i,10);    %Tx
        comb_12(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A312(i,13)+tab_FR01(i,11);                   %Ty
        comb_12(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_12(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A312(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_12(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A312(i,7)+tab_FR01(i,5);       %N
        comb_12(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A312(i,8)+tab_FR01(i,6);       %Tx
        comb_12(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A312(i,9)+tab_FR01(i,7);                      %Ty
        comb_12(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_12(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A312(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_12(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A312(i,11)+tab_FR01(i,9);     %N
        comb_12(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A312(i,12)+tab_FR01(i,10);    %Tx
        comb_12(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A312(i,13)+tab_FR01(i,11);                   %Ty
        comb_12(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_12(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A312(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%13)% PP AQ VT1 VT1A3 A312 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_13(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_13(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A312(i,7)+tab_FR02(i,5);       %N
        comb_13(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A312(i,8)+tab_FR02(i,6);       %Tx
        comb_13(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A312(i,9)+tab_FR02(i,7);                      %Ty
        comb_13(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_13(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A312(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_13(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A312(i,11)+tab_FR02(i,9);     %N
        comb_13(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A312(i,12)+tab_FR02(i,10);    %Tx
        comb_13(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A312(i,13)+tab_FR02(i,11);                   %Ty
        comb_13(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_13(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A312(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_13(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A312(i,7)+tab_FR02(i,5);       %N
        comb_13(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A312(i,8)+tab_FR02(i,6);       %Tx
        comb_13(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A312(i,9)+tab_FR02(i,7);                      %Ty
        comb_13(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_13(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A312(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_13(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A312(i,11)+tab_FR02(i,9);     %N
        comb_13(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A312(i,12)+tab_FR02(i,10);    %Tx
        comb_13(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A312(i,13)+tab_FR02(i,11);                   %Ty
        comb_13(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_13(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A312(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%14)% PP AQ VT1 VT1A3 A321 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_14(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_14(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A321(i,7)+tab_FR01(i,5);       %N
        comb_14(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A321(i,8)+tab_FR01(i,6);       %Tx
        comb_14(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A321(i,9)+tab_FR01(i,7);                      %Ty
        comb_14(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_14(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A321(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_14(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A321(i,11)+tab_FR01(i,9);     %N
        comb_14(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A321(i,12)+tab_FR01(i,10);    %Tx
        comb_14(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A321(i,13)+tab_FR01(i,11);                   %Ty
        comb_14(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_14(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A321(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_14(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A321(i,7)+tab_FR01(i,5);       %N
        comb_14(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A321(i,8)+tab_FR01(i,6);       %Tx
        comb_14(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A321(i,9)+tab_FR01(i,7);                      %Ty
        comb_14(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_14(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A321(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_14(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A321(i,11)+tab_FR01(i,9);     %N
        comb_14(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A321(i,12)+tab_FR01(i,10);    %Tx
        comb_14(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A321(i,13)+tab_FR01(i,11);                   %Ty
        comb_14(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_14(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A321(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%15)% PP AQ VT1 VT1A3 A321 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_15(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_15(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A321(i,7)+tab_FR02(i,5);       %N
        comb_15(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A321(i,8)+tab_FR02(i,6);       %Tx
        comb_15(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A321(i,9)+tab_FR02(i,7);                      %Ty
        comb_15(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_15(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A321(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_15(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A321(i,11)+tab_FR02(i,9);     %N
        comb_15(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A321(i,12)+tab_FR02(i,10);    %Tx
        comb_15(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A321(i,13)+tab_FR02(i,11);                   %Ty
        comb_15(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_15(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A321(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_15(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A321(i,7)+tab_FR02(i,5);       %N
        comb_15(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A321(i,8)+tab_FR02(i,6);       %Tx
        comb_15(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A321(i,9)+tab_FR02(i,7);                      %Ty
        comb_15(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_15(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A321(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_15(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A321(i,11)+tab_FR02(i,9);     %N
        comb_15(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A321(i,12)+tab_FR02(i,10);    %Tx
        comb_15(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A321(i,13)+tab_FR02(i,11);                   %Ty
        comb_15(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_15(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A321(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
%16)% PP AQ VT1 VT1A3 A322 FR01 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_16(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_16(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A322(i,7)+tab_FR01(i,5);       %N
        comb_16(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A322(i,8)+tab_FR01(i,6);       %Tx
        comb_16(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A322(i,9)+tab_FR01(i,7);                      %Ty
        comb_16(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_16(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A322(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_16(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A322(i,11)+tab_FR01(i,9);     %N
        comb_16(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A322(i,12)+tab_FR01(i,10);    %Tx
        comb_16(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A322(i,13)+tab_FR01(i,11);                   %Ty
        comb_16(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_16(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A322(i,14)+tab_FR01(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_16(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A322(i,7)+tab_FR01(i,5);       %N
        comb_16(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A322(i,8)+tab_FR01(i,6);       %Tx
        comb_16(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A322(i,9)+tab_FR01(i,7);                      %Ty
        comb_16(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_16(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A322(i,10)+tab_FR01(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_16(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A322(i,11)+tab_FR01(i,9);     %N
        comb_16(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A322(i,12)+tab_FR01(i,10);    %Tx
        comb_16(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A322(i,13)+tab_FR01(i,11);                   %Ty
        comb_16(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_16(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A322(i,14)+tab_FR01(i,12);   %Mx
    end
end
% =========================================================================
%17)% PP AQ VT1 VT1A3 A322 FR02 ===========================================
% =========================================================================
for i=1:24
    % colonna 1: orario (1:24)
    comb_17(i,1)=i;
    if tab_TMA(i,2)==0
        % LATO MANTOVA ====================================================
        comb_17(i,2)=tab_PP00(i,7)+tab_AQD0(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A322(i,7)+tab_FR02(i,5);       %N
        comb_17(i,3)=tab_PP00(i,8)+tab_AQD0(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A322(i,8)+tab_FR02(i,6);       %Tx
        comb_17(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A322(i,9)+tab_FR02(i,7);                      %Ty
        comb_17(i,5)=tab_AQD0(i,13);                                                                              %Tya
        comb_17(i,6)=tab_PP00(i,10)+tab_AQD0(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A322(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_17(i,7)=tab_PP00(i,11)+tab_AQD0(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A322(i,11)+tab_FR02(i,9);     %N
        comb_17(i,8)=tab_PP00(i,12)+tab_AQD0(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A322(i,12)+tab_FR02(i,10);    %Tx
        comb_17(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A322(i,13)+tab_FR02(i,11);                   %Ty
        comb_17(i,10)=tab_AQD0(i,17);                                                                             %Tya
        comb_17(i,11)=tab_PP00(i,14)+tab_AQD0(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A322(i,14)+tab_FR02(i,12);   %Mx
    else
        % LATO MANTOVA ====================================================
        comb_17(i,2)=tab_PP00(i,7)+tab_AQD1(i,11)+tab_VT0(i,13)+tab_VT1A3(i,13)+tab_A322(i,7)+tab_FR02(i,5);       %N
        comb_17(i,3)=tab_PP00(i,8)+tab_AQD1(i,12)+tab_VT0(i,14)+tab_VT1A3(i,14)+tab_A322(i,8)+tab_FR02(i,6);       %Tx
        comb_17(i,4)=tab_PP00(i,9)+tab_VT0(i,15)+tab_VT1A3(i,15)+tab_A322(i,9)+tab_FR02(i,7);                      %Ty
        comb_17(i,5)=tab_AQD1(i,13);                                                                              %Tya
        comb_17(i,6)=tab_PP00(i,10)+tab_AQD1(i,14)+tab_VT0(i,16)+tab_VT1A3(i,16)+tab_A322(i,10)+tab_FR02(i,8);     %Mx
        % LATO MODENA =====================================================
        comb_17(i,7)=tab_PP00(i,11)+tab_AQD1(i,15)+tab_VT0(i,17)+tab_VT1A3(i,17)+tab_A322(i,11)+tab_FR02(i,9);     %N
        comb_17(i,8)=tab_PP00(i,12)+tab_AQD1(i,16)+tab_VT0(i,18)+tab_VT1A3(i,18)+tab_A322(i,12)+tab_FR02(i,10);    %Tx
        comb_17(i,9)=tab_PP00(i,13)+tab_VT0(i,19)+tab_VT1A3(i,19)+tab_A322(i,13)+tab_FR02(i,11);                   %Ty
        comb_17(i,10)=tab_AQD1(i,17);                                                                             %Tya
        comb_17(i,11)=tab_PP00(i,14)+tab_AQD1(i,18)+tab_VT0(i,20)+tab_VT1A3(i,20)+tab_A322(i,14)+tab_FR02(i,12);   %Mx
    end
end
% =========================================================================
% Matrice tridimensionale che raccoglie tutte le combinazioni (1-17) ======
mat_comb(:,:,1)=comb_01;  mat_comb(:,:,2)=comb_02; %=======================
mat_comb(:,:,3)=comb_03;  mat_comb(:,:,4)=comb_04; %=======================
mat_comb(:,:,5)=comb_05;  mat_comb(:,:,6)=comb_06; %=======================
mat_comb(:,:,7)=comb_07;  mat_comb(:,:,8)=comb_08; %=======================
mat_comb(:,:,9)=comb_09;  mat_comb(:,:,10)=comb_10; %======================
mat_comb(:,:,11)=comb_11; mat_comb(:,:,12)=comb_12; %======================
mat_comb(:,:,13)=comb_13; mat_comb(:,:,14)=comb_14; %======================
mat_comb(:,:,15)=comb_15; mat_comb(:,:,16)=comb_16; %======================
mat_comb(:,:,17)=comb_17; %================================================
% =========================================================================
pause(0.5);
set(handles.radiobutton4,'Value',1);
pause(0.5);
% =========================================================================
% Il passo successivo è applicare la generica combinazione al modello
% strutturale per determinare le sollecitazioni agli incastri, per i 6 pali
% che compongono la pila 30.
% ========================================================================= 
% parametri utili letti da txt modello
d=str2num(setup{18,1}); k=str2num(setup{22,1}); h1=str2num(setup{20,1});

% =========================================================================
% ============================ mat_comb_pali ==============================
% =========================================================================
for j=1:17
    for i=1:24
        % distanza che dipende dalla quota del fondo
        L2=str2num(setup{24,1})-tab_PP00(i,2)+k;
        % ripartizione della spinta dell'acqua H in H1 e H2
        if tab_AQD0(i,2)>str2num(setup{24,1})
            h1aq=h1;
            if tab_TMA(i,2)==0
                H=tab_AQD0(i,10)/2;
                MH=H*((tab_AQD0(i,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00(i,2)+k);
            end
            if tab_TMA(i,2)==1
                H=tab_AQD1(i,10)/2;
                MH=H*((tab_AQD1(i,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00(i,2)+k);
            end
            H1=MH/(h1aq+L2);
            H2=H-H1;
        else
            h1aq=0;
            if tab_TMA(i,2)==0
                H=tab_AQD0(i,10)/2;
                MH=H*((tab_AQD0(i,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00(i,2)+k);
            end
            if tab_TMA(i,2)==1
                H=tab_AQD1(i,10)/2;
                MH=H*((tab_AQD1(i,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00(i,2)+k);
            end
            H1=MH/(h1aq+L2);
            H2=H-H1;
        end
        % colonna 1: orario (1-24)
        mat_comb_pali(i,1,j)=i;
        % =================================================================
        % pali lato MANTOVA: P5, P3, P1 (da monte a valle) ================
        % =================================================================
        % palo P1 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali(i,2,j)=mat_comb(i,2,j)/3+mat_comb(i,6,j)/d+...
            mat_comb(i,4,j)/d*(h1+L2/2)+H1/d*(h1aq+L2/2);
        % palo P1 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,3,j)=mat_comb(i,3,j)/3;
        % palo P1 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,4,j)=mat_comb(i,4,j)/3+H/3;
        % palo P1 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,5,j)=mat_comb(i,4,j)*L2/6+H1*L2/6;
        % palo P1 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,6,j)=mat_comb(i,3,j)*(L2+h1)/3;
        % =================================================================
        % palo P3 - sollecitazione assiale N (N)
        mat_comb_pali(i,7,j)=mat_comb(i,2,j)/3;
        % palo P3 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,8,j)=mat_comb(i,3,j)/3;
        % palo P3 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,9,j)=mat_comb(i,4,j)/3+H/3;
        % palo P3 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,10,j)=mat_comb(i,4,j)*L2/6+H1*L2/6;
        % palo P3 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,11,j)=mat_comb(i,3,j)*(L2+h1)/3;
        % =================================================================
        % palo P5 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali(i,12,j)=mat_comb(i,2,j)/3-mat_comb(i,6,j)/d-...
            mat_comb(i,4,j)/d*(h1+L2/2)-H1/d*(h1aq+L2/2);
        % palo P5 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,13,j)=mat_comb(i,3,j)/3;
        % palo P5 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,14,j)=mat_comb(i,4,j)/3+H/3;
        % palo P5 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,15,j)=mat_comb(i,4,j)*L2/6+H1*L2/6;
        % palo P5 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,16,j)=mat_comb(i,3,j)*(L2+h1)/3;
        % =================================================================
        % pali lato MODENA: P6, P4, P2 (da monte a valle) =================
        % =================================================================
        % palo P2 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali(i,17,j)=mat_comb(i,7,j)/3+mat_comb(i,11,j)/d+...
            mat_comb(i,9,j)/d*(h1+L2/2)+H1/d*(h1aq+L2/2);
        % palo P2 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,18,j)=mat_comb(i,8,j)/3;
        % palo P2 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,19,j)=mat_comb(i,9,j)/3+H/3;
        % palo P2 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,20,j)=mat_comb(i,9,j)*L2/6+H1*L2/6;
        % palo P2 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,21,j)=mat_comb(i,8,j)*(L2+h1)/3;
        % =================================================================
        % palo P4 - sollecitazione assiale N (N)
        mat_comb_pali(i,22,j)=mat_comb(i,7,j)/3;
        % palo P4 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,23,j)=mat_comb(i,8,j)/3;
        % palo P4 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,24,j)=mat_comb(i,9,j)/3+H/3;
        % palo P4 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,25,j)=mat_comb(i,9,j)*L2/6+H1*L2/6;
        % palo P4 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,26,j)=mat_comb(i,8,j)*(L2+h1)/3;
        % =================================================================
        % palo P6 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali(i,27,j)=mat_comb(i,7,j)/3-mat_comb(i,11,j)/d-...
            mat_comb(i,9,j)/d*(h1+L2/2)-H1/d*(h1aq+L2/2);
        % palo P6 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali(i,28,j)=mat_comb(i,8,j)/3;
        % palo P6 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali(i,29,j)=mat_comb(i,9,j)/3+H/3;
        % palo P6 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali(i,30,j)=mat_comb(i,9,j)*L2/6+H1*L2/6;
        % palo P6 - sollecitazione flettente My (Tx)
        mat_comb_pali(i,31,j)=mat_comb(i,8,j)*(L2+h1)/3;
    end
end
% =========================================================================
% COEFFICIENTE DI SICUREZZA ===============================================
% =========================================================================
% per essere congruente con il file excel, in modo da poter effettuare dei
% confronti, genero una matrice con all'interno le seguenti informazioni:
% COLONNA 1: ora della giornata
% COLONNA 2: valore del carico assiale
% COLONNA 3: valore del taglio totale Ttot=radq(Tx^2+Ty^2)
% COLONNA 4: valore del momento totale Mtot=radq(Mx^2+My^2)
% COLONNA 5: eccentricità (e=M/N)
% a parte la colonna 1 le colonne da 2 a 5 sono ripetute per i 6 pali...
global mat_comb_carichiT_ecc;

for j=1:17
    for i=1:24
        mat_comb_carichiT_ecc(i,1,j)=i;
        % palo P1 (Lato Mantova, Valle)====================================
        mat_comb_carichiT_ecc(i,2,j)=mat_comb_pali(i,2,j);               %N
        mat_comb_carichiT_ecc(i,3,j)=sqrt(mat_comb_pali(i,3,j)^2+mat_comb_pali(i,4,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,4,j)=sqrt(mat_comb_pali(i,5,j)^2+mat_comb_pali(i,6,j)^2);       %Mtot
        mat_comb_carichiT_ecc(i,5,j)=mat_comb_carichiT_ecc(i,4,j)/mat_comb_carichiT_ecc(i,2,j); %e=Mtot/N
        % palo P3 (Lato Mantova, Centro)===================================
        mat_comb_carichiT_ecc(i,6,j)=mat_comb_pali(i,7,j);               %N
        mat_comb_carichiT_ecc(i,7,j)=sqrt(mat_comb_pali(i,8,j)^2+mat_comb_pali(i,9,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,8,j)=sqrt(mat_comb_pali(i,10,j)^2+mat_comb_pali(i,11,j)^2);     %Ttot
        mat_comb_carichiT_ecc(i,9,j)=mat_comb_carichiT_ecc(i,8,j)/mat_comb_carichiT_ecc(i,6,j); %e=Mtot/N
        % palo P5 (Lato Mantova, Monte)====================================
        mat_comb_carichiT_ecc(i,10,j)=mat_comb_pali(i,12,j);             %N
        mat_comb_carichiT_ecc(i,11,j)=sqrt(mat_comb_pali(i,13,j)^2+mat_comb_pali(i,14,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,12,j)=sqrt(mat_comb_pali(i,15,j)^2+mat_comb_pali(i,16,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,13,j)=mat_comb_carichiT_ecc(i,12,j)/mat_comb_carichiT_ecc(i,10,j); %e=Mtot/N
        % palo P2 (Lato Modena, Valle)=====================================
        mat_comb_carichiT_ecc(i,14,j)=mat_comb_pali(i,17,j);             %N
        mat_comb_carichiT_ecc(i,15,j)=sqrt(mat_comb_pali(i,18,j)^2+mat_comb_pali(i,19,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,16,j)=sqrt(mat_comb_pali(i,20,j)^2+mat_comb_pali(i,21,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,17,j)=mat_comb_carichiT_ecc(i,16,j)/mat_comb_carichiT_ecc(i,14,j); %e=Mtot/N
        % palo P4 (Lato Modena, Centro)====================================
        mat_comb_carichiT_ecc(i,18,j)=mat_comb_pali(i,22,j);             %N
        mat_comb_carichiT_ecc(i,19,j)=sqrt(mat_comb_pali(i,23,j)^2+mat_comb_pali(i,24,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,20,j)=sqrt(mat_comb_pali(i,25,j)^2+mat_comb_pali(i,26,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,21,j)=mat_comb_carichiT_ecc(i,20,j)/mat_comb_carichiT_ecc(i,18,j); %e=Mtot/N
        % palo P6 (Lato Modena, Monte)=====================================
        mat_comb_carichiT_ecc(i,22,j)=mat_comb_pali(i,27,j);             %N
        mat_comb_carichiT_ecc(i,23,j)=sqrt(mat_comb_pali(i,28,j)^2+mat_comb_pali(i,29,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,24,j)=sqrt(mat_comb_pali(i,30,j)^2+mat_comb_pali(i,31,j)^2);       %Ttot
        mat_comb_carichiT_ecc(i,25,j)=mat_comb_carichiT_ecc(i,24,j)/mat_comb_carichiT_ecc(i,22,j); %e=Mtot/N
    end
end

% matrice con all'interno il valore del carico assiale N per ogni palo e
% l'eccentricità max
for j=1:17
    for i=1:24
        mat_comb_ecc(i,1,j)=i;
        % palo P1 (Lato Mantova, Valle)====================================
        mat_comb_ecc(i,2,j)=mat_comb_pali(i,2,j); % N
        mat_comb_ecc(i,3,j)=max(abs(mat_comb_pali(i,5,j)/mat_comb_pali(i,2,j)),...
            abs(mat_comb_pali(i,6,j)/mat_comb_pali(i,2,j))); % max(ex=Mx/N,ey=My/N)
        % palo P3 (Lato Mantova, Centro)===================================
        mat_comb_ecc(i,4,j)=mat_comb_pali(i,7,j); % N
        mat_comb_ecc(i,5,j)=max(abs(mat_comb_pali(i,10,j)/mat_comb_pali(i,7,j)),...
            abs(mat_comb_pali(i,11,j)/mat_comb_pali(i,7,j))); % max(ex=Mx/N,ey=My/N)
        % palo P5 (Lato Mantova, Monte)====================================
        mat_comb_ecc(i,6,j)=mat_comb_pali(i,12,j); % N
        mat_comb_ecc(i,7,j)=max(abs(mat_comb_pali(i,15,j)/mat_comb_pali(i,12,j)),...
            abs(mat_comb_pali(i,16,j)/mat_comb_pali(i,12,j))); % max(ex=Mx/N,ey=My/N) 
        % palo P2 (Lato Modena, Valle)=====================================
        mat_comb_ecc(i,8,j)=mat_comb_pali(i,17,j); % N
        mat_comb_ecc(i,9,j)=max(abs(mat_comb_pali(i,20,j)/mat_comb_pali(i,17,j)),...
            abs(mat_comb_pali(i,21,j)/mat_comb_pali(i,17,j))); % max(ex=Mx/N,ey=My/N)
        % palo P4 (Lato Modena, Centro)====================================
        mat_comb_ecc(i,10,j)=mat_comb_pali(i,22,j); % N
        mat_comb_ecc(i,11,j)=max(abs(mat_comb_pali(i,25,j)/mat_comb_pali(i,22,j)),...
            abs(mat_comb_pali(i,26,j)/mat_comb_pali(i,22,j))); % max(ex=Mx/N,ey=My/N)
        % palo P6 (Lato Modena, Monte)=====================================
        mat_comb_ecc(i,12,j)=mat_comb_pali(i,27,j); % N
        mat_comb_ecc(i,13,j)=max(abs(mat_comb_pali(i,30,j)/mat_comb_pali(i,27,j)),...
            abs(mat_comb_pali(i,31,j)/mat_comb_pali(i,27,j))); % max(ex=Mx/N,ey=My/N)
    end
end

% acquisizione dei parametri utili per le verifiche di pressoflessione
Rgi=str2num(setup{36,1});     % raggio giratore di inerzia
Rcls=str2num(setup{28,1});    % raggio palo cls
R1=str2num(setup{30,1});      % raggio posizionamento armatura
Dferro=str2num(setup{32,1});  % diametro barra acciaio
num=str2num(setup{34,1});     % numero delle barre
n=str2num(setup{38,1});       % n=15 rapporto moduli elastici


Acls=pi()*Rcls^2;                   % area della sezione del palo in cls
Aferrotot=num*(pi()*Dferro^2)/4;    % area totale dell'armatura
Aomog=Acls+n*Aferrotot;             % area omogeneizzata
Jcls=pi()*Rcls^4/4;                 % mom. inerzia area cls

a_cls=str2num(setup{43,1}); b_cls=str2num(setup{45,1});      
c_cls=str2num(setup{47,1}); d_cls=str2num(setup{49,1});
f_cls=str2num(setup{51,1});

a_acc=str2num(setup{56,1}); b_acc=str2num(setup{58,1});      
c_acc=str2num(setup{60,1}); d_acc=str2num(setup{62,1});      
f_acc=str2num(setup{64,1});


for j=1:17
    for i=1:24
        mat_comb_sigma(i,1,j)=i;
        % CASO PICCOLA ECCENTRICITA' ======================================
        % sezione completamente reagente, l'acciaio non lavora....
        % =================================================================
        
        % palo P1 (Lato Mantova, Valle)====================================
        if mat_comb_ecc(i,3,j)<=Rgi % piccola eccentricità
            mat_comb_sigma(i,2,j)=mat_comb_ecc(i,2,j)/1000/Aomog+...
                mat_comb_ecc(i,2,j)*mat_comb_ecc(i,3,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,3,j)=0;
        else      % grande eccentricità    
            ecc=mat_comb_ecc(i,3,j);
            mat_comb_sigma(i,2,j)=mat_comb_ecc(i,2,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,3,j)=mat_comb_ecc(i,2,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end
        % palo P3 (Lato Mantova, Centro)===================================    
        if mat_comb_ecc(i,5,j)<=Rgi % piccola eccentricità    
            mat_comb_sigma(i,4,j)=mat_comb_ecc(i,4,j)/1000/Aomog+...
                mat_comb_ecc(i,4,j)*mat_comb_ecc(i,5,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,5,j)=0;
        else      % grande eccentricità        
            ecc=mat_comb_ecc(i,5,j);
            mat_comb_sigma(i,4,j)=mat_comb_ecc(i,4,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,5,j)=mat_comb_ecc(i,4,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end
        % palo P5 (Lato Mantova, Monte)====================================
        if mat_comb_ecc(i,7,j)<=Rgi % piccola eccentricità       
            mat_comb_sigma(i,6,j)=mat_comb_ecc(i,6,j)/1000/Aomog+...
                mat_comb_ecc(i,6,j)*mat_comb_ecc(i,7,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,7,j)=0;
        else      % grande eccentricità   
            ecc=mat_comb_ecc(i,7,j);
            mat_comb_sigma(i,6,j)=mat_comb_ecc(i,6,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,7,j)=mat_comb_ecc(i,6,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end
        % palo P2 (Lato Modena, Valle)=====================================
        if mat_comb_ecc(i,9,j)<=Rgi % piccola eccentricità       
            mat_comb_sigma(i,8,j)=mat_comb_ecc(i,8,j)/1000/Aomog+...
                mat_comb_ecc(i,8,j)*mat_comb_ecc(i,9,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,9,j)=0;
        else      % grande eccentricità       
            ecc=mat_comb_ecc(i,9,j);
            mat_comb_sigma(i,8,j)=mat_comb_ecc(i,8,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,9,j)=mat_comb_ecc(i,8,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end
        % palo P4 (Lato Modena, Centro)====================================
        if mat_comb_ecc(i,11,j)<=Rgi % piccola eccentricità     
            mat_comb_sigma(i,10,j)=mat_comb_ecc(i,10,j)/1000/Aomog+...
                mat_comb_ecc(i,10,j)*mat_comb_ecc(i,11,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,11,j)=0;
        else      % grande eccentricità      
            ecc=mat_comb_ecc(i,11,j);
            mat_comb_sigma(i,10,j)=mat_comb_ecc(i,10,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,11,j)=mat_comb_ecc(i,10,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end
        % palo P6 (Lato Modena, Monte)=====================================
        if mat_comb_ecc(i,13,j)<=Rgi % piccola eccentricità       
            mat_comb_sigma(i,12,j)=mat_comb_ecc(i,12,j)/1000/Aomog+...
                mat_comb_ecc(i,12,j)*mat_comb_ecc(i,13,j)/1000/Jcls*Rcls;
            mat_comb_sigma(i,13,j)=0;
        else      % grande eccentricità
            ecc=mat_comb_ecc(i,13,j);
            mat_comb_sigma(i,12,j)=mat_comb_ecc(i,12,j)/1000*...
                (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
            mat_comb_sigma(i,13,j)=mat_comb_ecc(i,12,j)/1000*...
                (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
        end        
    end
end

cls_amm=str2num(setup{169,1}); cls_lim=str2num(setup{171,1});      
acc_amm=str2num(setup{176,1}); acc_lim=str2num(setup{178,1});

for j=1:17
    for i=1:24
        mat_comb_sicurezza(i,1,j)=i;
        % palo P1 (Lato Mantova, Valle)================================
        mat_comb_sicurezza(i,2,j)=cls_amm/mat_comb_sigma(i,2,j);
        mat_comb_sicurezza(i,3,j)=acc_amm/mat_comb_sigma(i,3,j);
        % palo P3 (Lato Mantova, Centro)===============================
        mat_comb_sicurezza(i,4,j)=cls_amm/mat_comb_sigma(i,4,j);
        mat_comb_sicurezza(i,5,j)=acc_amm/mat_comb_sigma(i,5,j);   
        % palo P5 (Lato Mantova, Monte)================================
        mat_comb_sicurezza(i,6,j)=cls_amm/mat_comb_sigma(i,6,j);
        mat_comb_sicurezza(i,7,j)=acc_amm/mat_comb_sigma(i,7,j);   
        % palo P2 (Lato Modena, Valle)=================================
        mat_comb_sicurezza(i,8,j)=cls_amm/mat_comb_sigma(i,8,j);
        mat_comb_sicurezza(i,9,j)=acc_amm/mat_comb_sigma(i,9,j);   
        % palo P4 (Lato Modena, Centro)================================
        mat_comb_sicurezza(i,10,j)=cls_amm/mat_comb_sigma(i,10,j);
        mat_comb_sicurezza(i,11,j)=acc_amm/mat_comb_sigma(i,11,j);   
        % palo P6 (Lato Modena, Monte)=================================
        mat_comb_sicurezza(i,12,j)=cls_amm/mat_comb_sigma(i,12,j);
        mat_comb_sicurezza(i,13,j)=acc_amm/mat_comb_sigma(i,13,j);   
    end
end

for j=1:17
    for i=1:24
        mat_comb_sicurezza_max(i,1,j)=i;
        % palo P1 (Lato Mantova, Valle)================================
        mat_comb_sicurezza_max(i,2,j)=min(mat_comb_sicurezza(i,2,j),mat_comb_sicurezza(i,3,j));
        % palo P3 (Lato Mantova, Centro)===============================
        mat_comb_sicurezza_max(i,3,j)=min(mat_comb_sicurezza(i,4,j),mat_comb_sicurezza(i,5,j));
        % palo P5 (Lato Mantova, Monte)================================
        mat_comb_sicurezza_max(i,4,j)=min(mat_comb_sicurezza(i,6,j),mat_comb_sicurezza(i,7,j));
        % palo P2 (Lato Modena, Valle)=================================
        mat_comb_sicurezza_max(i,5,j)=min(mat_comb_sicurezza(i,8,j),mat_comb_sicurezza(i,9,j));
        % palo P4 (Lato Modena, Centro)================================
        mat_comb_sicurezza_max(i,6,j)=min(mat_comb_sicurezza(i,10,j),mat_comb_sicurezza(i,11,j));
        % palo P6 (Lato Modena, Monte)=================================
        mat_comb_sicurezza_max(i,7,j)=min(mat_comb_sicurezza(i,12,j),mat_comb_sicurezza(i,13,j));
    end
end

global mat_comb_sic_fin_pali;

for i=1:24
    for k=2:7
        for j=1:17
            dato=mat_comb_sicurezza_max(i,k,j);
            vettore(j,1)=dato;
        end
        mat_comb_sic_fin_pali(i,1)=i;
        mat_comb_sic_fin_pali(i,k)=min(vettore);
    end
end

global mat_comb_sic_finale;

for i=1:24
    for k=2:7
        dato=mat_comb_sic_fin_pali(i,k);
        vettore_min(k-1,1)=dato;
    end
    mat_comb_sic_finale(i,1)=i;
    mat_comb_sic_finale(i,2)=min(vettore_min);
end

pause(0.5);
set(handles.radiobutton5,'Value',1);
set(handles.pushbutton22,'BackgroundColor',[0 1 0]);
pause(0.5);
% =========================================================================
% rappresentazione dell'andamento del coefficiente di sicurezza
% =========================================================================
% dati da plottare del coefficiente di sicurezza nel tempo fino all'ora
% attuale. Ricordo che nella preparazione delle tabelle ho sempre compilato
% tutte e 24 le righe....

for i=1:orack
    if mat_comb_sic_finale(i,1)<=orack
        x(i,1)=mat_comb_sic_finale(i,1);
        y(i,1)=mat_comb_sic_finale(i,2);
    end
end

% valutazione del massimo Y
y_max=max(y);

axes(handles.axes1);

% plottaggio coefficiente di sicurezza
plot(x,y,'k','LineWidth',2);
hold on
% plottaggio limite = 1
for i=1:orack
    sic(i,1)=1;
end
plot(x,sic,'r','LineWidth',2);
% plottaggio ora attuale
ora_asse_x=[orack orack orack orack orack];
ora_asse_y=linspace(0,y_max+0.5,5);

plot(ora_asse_x,ora_asse_y,'m','LineWidth',2);

axis([1 24 0 y_max+0.5]);
lab=linspace(1,24,24);
set(handles.axes1,'XTick',lab);

xlabel('ore della giornata');
ylabel('coeff. di sicurezza [CS]');
legend('Coeff. sicurezza','Limite sicurezza','Ora corrente','Best');
hold off

% rappresentazione della pila 30 ==========================================

axes(handles.axes3);
cartella=('D:\Borgoforte\Programma Matlab Borgoforte');
cd(cartella);
pila_30=imread('PILA_30A.jpg');

imshow(pila_30);

% determinazione delle informazioni aggiuntive ============================
% =========================================================================
% A) Qual'è il palo più sollecitato?
% B) Qual'è la conbinazione di carico associata?
% C) Piccola o grande eccentricità?
% D) Critico lato cls o acciaio?
% E) Valore del coefficiente di sicurezza? 
% =========================================================================

% E) Valore del coefficiente di sicurezza =================================
global CS_orack_ok;
CS_orack=mat_comb_sic_finale(orack,2);
CS_orack_ok=chop(CS_orack,3);
CS_orack_str=num2str(CS_orack_ok);
set(handles.text23,'string',CS_orack_str);
if CS_orack>1.5
    set(handles.text23,'ForegroundColor',[0,1,0]);
end
if CS_orack>1.2 && CS_orack<=1.5 
    set(handles.text23,'ForegroundColor',[1,1,0]);
end
if CS_orack<=1.2
    set(handles.text23,'ForegroundColor',[1,0,0]);
end




% A) qual'è il palo più sollecitato? ======================================
global colonna;
for k=2:7
    if mat_comb_sic_fin_pali(orack,k)==CS_orack
        colonna=k;
    end
end
switch colonna
    case 2
        palo='P1'; Npalo=1;
    case 3
        palo='P3'; Npalo=3;
    case 4
        palo='P5'; Npalo=5;
    case 5
        palo='P2'; Npalo=2;
    case 6
        palo='P4'; Npalo=4;
    case 7
        palo='P6'; Npalo=6;
end
set(handles.text24,'string',palo);

% B) qual'è la conbinazione di carico associata? ==========================
global combinazione;
for j=1:17
    if mat_comb_sicurezza_max(orack,colonna,j)==CS_orack
        combinazione=j;
    end
end
switch combinazione
    case 1
        comb='comb_01';
    case 2
        comb='comb_02';
    case 3
        comb='comb_03';
    case 4
        comb='comb_04';
    case 5
        comb='comb_05';
    case 6
        comb='comb_06';
    case 7
        comb='comb_07';
    case 8
        comb='comb_08';
    case 9
        comb='comb_09';
    case 10
        comb='comb_10';
    case 11
        comb='comb_11';
    case 12
        comb='comb_12';
    case 13
        comb='comb_13';
    case 14
        comb='comb_14';
    case 15
        comb='comb_15';
    case 16
        comb='comb_16';
    case 17
        comb='comb_17';
end
set(handles.text26,'string',comb);

% C) Piccola o grande eccentricità? =======================================
% riga di riferimento matrice (i): orack
% colonna di riferimento (j): deve essere determinata sapendo il palo
% combinazione di riferimento (k): combinazione
switch Npalo
    case 1
        colonna_rif=3;
    case 2
        colonna_rif=9;
    case 3
        colonna_rif=5;
    case 4
        colonna_rif=11;
    case 5
        colonna_rif=7;
    case 6
        colonna_rif=13;
end
ecc_rif=mat_comb_ecc(orack,colonna_rif,combinazione);
if ecc_rif<=Rgi
    eccentricita=('Piccola'); Necc=0;
    set(handles.text27,'string',eccentricita);
end
if ecc_rif>Rgi
    eccentricita=('Grande');  Necc=1;
    set(handles.text27,'string',eccentricita);
end
% D) Critico lato cls o acciaio? ==========================================
if Necc==0
    MatCritico=('CLS');
    set(handles.text28,'string',MatCritico);
end
if Necc==1
    Mat_cls=mat_comb_sicurezza(orack,colonna_rif,combinazione);
    Mat_acc=mat_comb_sicurezza(orack,colonna_rif+1,combinazione);
    if Mat_cls<Mat_acc
        MatCritico=('CLS');
        set(handles.text28,'string',MatCritico);
    end
    if Mat_cls==Mat_acc
        MatCritico=('CLS/ACC');
        set(handles.text28,'string',MatCritico);
    end
    if Mat_cls>Mat_acc
        MatCritico=('ACCIAIO');
        set(handles.text28,'string',MatCritico);
    end
end
% =========================================================================
% tabella blocco 8: in realtà non è un'unica tabella ma sono 6 generate e
% posizionate per sembrare una sola. Non è possibile definirne una in
% quanto il popup menù della seconda colonna è diverso per ogni riga 
% (opzione non gestita dalla uitable)....
set(handles.text35,'string','');
% h acqua =================================================================
adesso_hacqua={num2str(chop(tab_AQD0(orack,2),3)),'0'};
set(handles.uitable3,'data',adesso_hacqua);
% V vento =================================================================
% considero la velocità ortogonale al ponte (+/- in base alla direzione 
% rispettivamente concorde o discorde alla direzione della corrente)
v_ortogonale=(tab_VT0(orack,5)*tab_VT0(orack,6));
v_ortogonale=chop(v_ortogonale,3);
adesso_vento={num2str(v_ortogonale),'0'};
set(handles.uitable4,'data',adesso_vento);
% Fondo (m slm) ===========================================================
fondo=(tab_PP00(orack,2));  fondo=chop(fondo,3);
adesso_fondo={num2str(fondo),'0'};
set(handles.uitable8,'data',adesso_fondo);
% Presenza Detriti? =======================================================
% tabella di riferimento TAB_TMO o TAB_TMA (identiche)
for i=1:24
    if orack==tab_TMA(i,1)
        if tab_TMA(i,2)==1
            adesso_detriti={'SI','SI'};
        else
            adesso_detriti={'NO','SI'};
        end
    end
end
set(handles.uitable5,'data',adesso_detriti);
% Ponte Aperto? ===========================================================
% per definizione si ipotizza che il ponte sia aperto al traffico
adesso_ponte={'SI','SI'};
set(handles.uitable6,'data',adesso_ponte);
% CS attuale ==============================================================
adesso_CS={CS_orack_str,'??'};
set(handles.uitable7,'data',adesso_CS);
% =========================================================================
% che FIGATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
% http://www.mathworks.it/it/help/matlab/ref/uitable.html
% =========================================================================






% =========================================================================
% PREVISIONE ==============================================================
% =========================================================================
% --- Executes on button press in pushbutton19.
function pushbutton19_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton19 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

global tab_PP00_utente;
global tab_VT0_utente;
global tab_VT1A1_utente;
global tab_VT1A2_utente;
global tab_VT1A3_utente;
global tab_AQ_utente;
global tab_TMA_utente;
global tab_TMO_utente;

global tab_PP00;  % tabella peso prorpio
global tab_AQD0; % tabella spinta acqua no detriti
global tab_AQD1; % tabella spinta acqua si detriti
global tab_VT0;  % tabella vento (no traffico)
global tab_VT1A1; % tabella vento (traffico A1)
global tab_VT1A2; % tabella vento (traffico A2)
global tab_VT1A3; % tabella vento (traffico A3)
global tab_FR01;  % tabella frenata dei veicoli
global tab_FR02;  % tabella frenata dei veicoli
global tab_A110; % tabella carichi mobili A1
global tab_A120; % tabella carichi mobili A1
global tab_A210; % tabella carichi mobili A2
global tab_A220; % tabella carichi mobili A2
global tab_A311; % tabella carichi mobili A3
global tab_A312; % tabella carichi mobili A3
global tab_A321; % tabella carichi mobili A3
global tab_A322; % tabella carichi mobili A3
global tab_TMA;  % tabella telecamera mantova
global tab_TMO;  % tabella telecamera modena

global set_modello; global setup;

global valori_hacqua;
global valori_vvento;
global valori_detriti;
global valori_traffico;

% Acquisizione delle informazioni inserite dall'utente
valori_hacqua=get(handles.uitable3,'data');
valori_vvento=get(handles.uitable4,'data');
valori_fondo=get(handles.uitable8,'data');
valori_detriti=get(handles.uitable5,'data');
valori_traffico=get(handles.uitable6,'data');
% Calcolo dei nuovi parametri
hacqua_utente=str2num(valori_hacqua{1,2});
vvento_utente=str2num(valori_vvento{1,2});
fondo_utente=str2num(valori_fondo{1,2});
if valori_detriti{1,2}=='SI'
    detriti_utente=1;
else
    detriti_utente=0;
end
global traffico_utente;
if valori_traffico{1,2}=='SI'
    traffico_utente=1;
else
    traffico_utente=0;
end
% calcolo delle forzanti (il procedimento è simile alle operazioni del 
% tasto (2) CALCOLO FORZANTI del blocco (6) coeff. di sicurezza)......
% però la generica tabella in realtà ha una sola riga in quanto ho un
% valore preciso impostato dall'utente................................
% =========================================================================
% ================== PESO PROPRIO DELLA STRUTTURA =========================
%k: eventuale affondamendo dell'incastro rispetto alla quota del fondo
parK=str2num(setup{22,1}); % da file txt
%acquisizione quota traverso inferiore per determinare la lunghezza dei
%pali fuori dal fondo
parTr=str2num(setup{24,1}); % da file txt
%determinazione del peso proprio della pila fino al traverso compreso
parPP=str2num(setup{183,1})+str2num(setup{185,1})+...
    str2num(setup{187,1})+str2num(setup{189,1});
parPmetro=str2num(setup{191,1});
% compilazione tabella peso proprio della struttura
tab_PP00_utente(1,1)=1;
tab_PP00_utente(1,2)=fondo_utente;
tab_PP00_utente(1,3)=tab_PP00_utente(1,2)-parK;
tab_PP00_utente(1,4)=parTr-tab_PP00_utente(1,3);
tab_PP00_utente(1,5)=parPP+6*parPmetro*tab_PP00_utente(1,4);
tab_PP00_utente(1,6)=str2num(setup{196,1})*str2num(setup{198,1})/...
        str2num(setup{16,1});
% LATO MANTOVA ============================================================
tab_PP00_utente(1,7)=tab_PP00_utente(1,5)/2+tab_PP00_utente(1,6); % N
tab_PP00_utente(1,8)=0; % Tx
tab_PP00_utente(1,9)=0; % Ty
tab_PP00_utente(1,10)=0; %Mx
% LATO MODENA =============================================================
tab_PP00_utente(1,11)=tab_PP00_utente(1,5)/2-tab_PP00_utente(1,6); % N
tab_PP00_utente(1,12)=0; % Tx
tab_PP00_utente(1,13)=0; % Ty
tab_PP00_utente(1,14)=0; %Mx
% =========================================================================
% =================== SPINTA IDRODINAMICA ACQUA ===========================
tab_AQ_utente(1,1)=1;
tab_AQ_utente(1,2)=hacqua_utente;
tab_AQ_utente(1,3)=str2num(setup{26,1});
tab_AQ_utente(1,4)=tab_AQ_utente(1,2)-tab_AQ_utente(1,3);
if tab_AQ_utente(1,2)<=str2num(setup{85,1})
    tab_AQ_utente(1,5)=str2num(setup{87,1})*tab_AQ_utente(1,2)^2+...
        str2num(setup{89,1})*tab_AQ_utente(1,2)+...
        str2num(setup{91,1});
else
    if tab_AQ_utente(1,2)<=str2num(setup{93,1})
        tab_AQ_utente(1,5)=str2num(setup{95,1})*tab_AQ_utente(1,2)^2+...
            str2num(setup{97,1})*tab_AQ_utente(1,2)+...
            str2num(setup{99,1});
    else
        tab_AQ_utente(1,5)=str2num(setup{101,1})*tab_AQ_utente(1,2)^2+...
            str2num(setup{103,1})*tab_AQ_utente(1,2)+...
            str2num(setup{105,1});
    end
end
tab_AQ_utente(1,6)=str2num(setup{78,1})*tab_AQ_utente(1,4)^3+...
    str2num(setup{80,1})*tab_AQ_utente(1,4)^2+...
    str2num(setup{82,1})*tab_AQ_utente(1,4);
tab_AQ_utente(1,7)=str2num(setup{75,1}); % densità acqua
if detriti_utente==0
    tab_AQ_utente(1,8)=str2num(setup{69,1}); % Drag
    tab_AQ_utente(1,9)=str2num(setup{14,1})*tab_AQ_utente(1,4);
else
    tab_AQ_utente(1,8)=str2num(setup{71,1}); % Drag
    tab_AQ_utente(1,9)=str2num(setup{16,1})*tab_AQ_utente(1,4)*...
        str2num(setup{73,1});
end
if detriti_utente==0
    tab_AQ_utente(1,10)=2*(1/2*tab_AQ_utente(1,8)*tab_AQ_utente(1,7)*...
        tab_AQ_utente(1,9)*tab_AQ_utente(1,6)^2)/1000;
else
    tab_AQ_utente(1,10)=(1/2*tab_AQ_utente(1,8)*tab_AQ_utente(1,7)*...
        tab_AQ_utente(1,9)*tab_AQ_utente(1,6)^2)/1000;
end
% LATO MANTOVA ============================================================
tab_AQ_utente(1,11)=0; % N
tab_AQ_utente(1,12)=0; % Tx
tab_AQ_utente(1,13)=tab_AQ_utente(1,10)/2; % Ty
tab_AQ_utente(1,14)=0; %Mx
% LATO MODENA =============================================================
tab_AQ_utente(1,15)=0; % N
tab_AQ_utente(1,16)=0; % Tx
tab_AQ_utente(1,17)=tab_AQ_utente(1,10)/2; % Ty
tab_AQ_utente(1,18)=0; %Mx 
% =========================================================================
% ========================= SPINTA DEL VENTO ==============================
tab_VT0_utente(1,1)=1;
tab_VT0_utente(1,2)=vvento_utente;
if vvento_utente>0
    tab_VT0_utente(1,3)=90;
else
    tab_VT0_utente(1,3)=270;
end
tab_VT0_utente(1,4)=tab_VT0_utente(1,3);
tab_VT0_utente(1,5)=tab_VT0_utente(1,2);
if vvento_utente>0
    tab_VT0_utente(1,6)=1;
else
    tab_VT0_utente(1,6)=-1;
end 
tab_VT0_utente(1,7)=str2num(setup{112,1});
tab_VT0_utente(1,8)=str2num(setup{110,1});
for j=1:8
    tab_VT1A1_utente(1,j)=tab_VT0_utente(1,j);
    tab_VT1A2_utente(1,j)=tab_VT0_utente(1,j);
    tab_VT1A3_utente(1,j)=tab_VT0_utente(1,j);
end
tab_VT0_utente(1,9)=str2num(setup{123,1});
tab_VT1A1_utente(1,9)=str2num(setup{126,1})*str2num(setup{128,1});
tab_VT1A2_utente(1,9)=str2num(setup{126,1})*str2num(setup{128,1});
tab_VT1A3_utente(1,9)=str2num(setup{126,1})*str2num(setup{130,1});

tab_VT0_utente(1,10)=(1/2*tab_VT0_utente(1,7)*tab_VT0_utente(1,8)*...
    tab_VT0_utente(1,9)*tab_VT0_utente(1,5)^2)/1000;
tab_VT1A1_utente(1,10)=(1/2*tab_VT1A1_utente(1,7)*tab_VT1A1_utente(1,8)*...
    tab_VT1A1_utente(1,9)*tab_VT1A1_utente(1,5)^2)/1000;
tab_VT1A2_utente(1,10)=(1/2*tab_VT1A2_utente(1,7)*tab_VT1A2_utente(1,8)*...
    tab_VT1A2_utente(1,9)*tab_VT1A2_utente(1,5)^2)/1000;
tab_VT1A3_utente(1,10)=(1/2*tab_VT1A3_utente(1,7)*tab_VT1A3_utente(1,8)*...
    tab_VT1A3_utente(1,9)*tab_VT1A3_utente(1,5)^2)/1000;

tab_VT0_utente(1,11)=tab_VT0_utente(1,10)*str2num(setup{116,1})/str2num(setup{16,1});
tab_VT1A1_utente(1,11)=0; tab_VT1A2_utente(1,11)=0; tab_VT1A3_utente(1,11)=0;

tab_VT0_utente(1,12)=tab_VT0_utente(1,10)*str2num(setup{118,1});
tab_VT1A1_utente(1,12)=tab_VT1A1_utente(1,10)*str2num(setup{120,1});
tab_VT1A2_utente(1,12)=tab_VT1A2_utente(1,10)*str2num(setup{120,1});
tab_VT1A3_utente(1,12)=tab_VT1A3_utente(1,10)*str2num(setup{120,1});

% LATO MANTOVA ========================================================
    % no traffico
    tab_VT0_utente(1,13)=0; % N
    tab_VT0_utente(1,14)=0; % Tx
    tab_VT0_utente(1,15)=tab_VT0_utente(1,6)*...
        (tab_VT0_utente(1,10)/2+tab_VT0_utente(1,11)); % Ty + asimmetrico
    tab_VT0_utente(1,16)=tab_VT0_utente(1,6)*tab_VT0_utente(1,12)/2; % Mx
    % combinaziona A1
    tab_VT1A1_utente(1,13)=0; % N
    tab_VT1A1_utente(1,14)=0; % Tx
    tab_VT1A1_utente(1,15)=tab_VT1A1_utente(1,6)*tab_VT1A1_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A1_utente(1,16)=tab_VT1A1_utente(1,6)*tab_VT1A1_utente(1,12)/2; % Mx
    % combinaziona A2
    tab_VT1A2_utente(1,13)=0; % N
    tab_VT1A2_utente(1,14)=0; % Tx
    tab_VT1A2_utente(1,15)=tab_VT1A2_utente(1,6)*tab_VT1A2_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A2_utente(1,16)=tab_VT1A2_utente(1,6)*tab_VT1A2_utente(1,12)/2; % Mx
    % combinaziona A3
    tab_VT1A3_utente(1,13)=0; % N
    tab_VT1A3_utente(1,14)=0; % Tx
    tab_VT1A3_utente(1,15)=tab_VT1A3_utente(1,6)*tab_VT1A3_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A3_utente(1,16)=tab_VT1A3_utente(1,6)*tab_VT1A3_utente(1,12)/2; % Mx
    % LATO MODENA =========================================================
    % no traffico
    tab_VT0_utente(1,17)=0; % N
    tab_VT0_utente(1,18)=0; % Tx
    tab_VT0_utente(1,19)=tab_VT0_utente(1,6)*...
        (tab_VT0_utente(1,10)/2-tab_VT0_utente(1,11)); % Ty + asimmetrico
    tab_VT0_utente(1,20)=tab_VT0_utente(1,6)*tab_VT0_utente(1,12)/2; % Mx
    % combinaziona A1
    tab_VT1A1_utente(1,17)=0; % N
    tab_VT1A1_utente(1,18)=0; % Tx
    tab_VT1A1_utente(1,19)=tab_VT1A1_utente(1,6)*tab_VT1A1_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A1_utente(1,20)=tab_VT1A1_utente(1,6)*tab_VT1A1_utente(1,12)/2; % Mx
    % combinaziona A2
    tab_VT1A2_utente(1,17)=0; % N
    tab_VT1A2_utente(1,18)=0; % Tx
    tab_VT1A2_utente(1,19)=tab_VT1A2_utente(1,6)*tab_VT1A2_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A2_utente(1,20)=tab_VT1A2_utente(1,6)*tab_VT1A2_utente(1,12)/2; % Mx
    % combinaziona A3
    tab_VT1A3_utente(1,17)=0; % N
    tab_VT1A3_utente(1,18)=0; % Tx
    tab_VT1A3_utente(1,19)=tab_VT1A3_utente(1,6)*tab_VT1A3_utente(1,10)/2; % Ty (no asimmetrico)
    tab_VT1A3_utente(1,20)=tab_VT1A3_utente(1,6)*tab_VT1A3_utente(1,12)/2; % Mx
% =========================================================================
% ================ CALCOLO DELLA FRENATA DEI VEICOLI ======================
tab_FR01_utente(1,1)=1; % COLONNA 1: orario (1:24)
tab_FR01_utente(1,2)=str2num(setup{137,1}); % COLONNA 2: Forza Ty....
tab_FR01_utente(1,3)=tab_FR01_utente(1,2)*str2num(setup{135,1}); % COLONNA 3: Momento
tab_FR01_utente(1,4)=1; % COLONNA 4: direzizone frenata 1: MODENA>MANTOVA
% LATO MANTOVA ========================================================
tab_FR01_utente(1,5)=tab_FR01_utente(1,4)*(tab_FR01_utente(1,3)/str2num(setup{16,1})); % N
tab_FR01_utente(1,6)=tab_FR01_utente(1,4)*tab_FR01_utente(1,2)/2; % Tx
tab_FR01_utente(1,7)=0; % Ty
tab_FR01_utente(1,8)=0; % Mx
% LATO MODENA =========================================================
tab_FR01_utente(1,9)=-tab_FR01_utente(1,4)*(tab_FR01_utente(1,3)/str2num(setup{16,1})); % N
tab_FR01_utente(1,10)=tab_FR01_utente(1,4)*tab_FR01_utente(1,2)/2; % Tx
tab_FR01_utente(1,11)=0; % Ty
tab_FR01_utente(1,12)=0; % Mx

tab_FR02_utente(1,1)=1; % COLONNA 1: orario (1:24)
tab_FR02_utente(1,2)=str2num(setup{137,1}); % COLONNA 2: Forza Ty....
tab_FR02_utente(1,3)=tab_FR02_utente(1,2)*str2num(setup{135,1}); % COLONNA 3: Momento
tab_FR02_utente(1,4)=-1; % COLONNA 4: direzizone frenata -1: MANTOVA>MODENA
% LATO MANTOVA ========================================================
tab_FR02_utente(1,5)=tab_FR02_utente(1,4)*(tab_FR02_utente(1,3)/str2num(setup{16,1})); % N
tab_FR02_utente(1,6)=tab_FR02_utente(1,4)*tab_FR02_utente(1,2)/2; % Tx
tab_FR02_utente(1,7)=0; % Ty
tab_FR02_utente(1,8)=0; % Mx
% LATO MODENA =========================================================
tab_FR02_utente(1,9)=-tab_FR02_utente(1,4)*(tab_FR02_utente(1,3)/str2num(setup{16,1})); % N
tab_FR02_utente(1,10)=tab_FR02_utente(1,4)*tab_FR02_utente(1,2)/2; % Tx
tab_FR02_utente(1,11)=0; % Ty
tab_FR02_utente(1,12)=0; % Mx
% =========================================================================
% ================== CARICHI MOBILI COMBINAZIONI ==========================
for j=1:14
    tab_A110_utente(1,j)=tab_A110(1,j);
    tab_A120_utente(1,j)=tab_A120(1,j);
    tab_A210_utente(1,j)=tab_A210(1,j);
    tab_A220_utente(1,j)=tab_A220(1,j);
    tab_A311_utente(1,j)=tab_A311(1,j);
    tab_A312_utente(1,j)=tab_A312(1,j);
    tab_A321_utente(1,j)=tab_A321(1,j);
    tab_A322_utente(1,j)=tab_A322(1,j);
end
% =========================================================================
% ==================== TELECAMERE MANTOVA/MODENA ==========================
tab_TMA_utente(1,1)=1;
tab_TMA_utente(1,2)=detriti_utente;
tab_TMO_utente(1,1)=1;
tab_TMO_utente(1,2)=detriti_utente;

% =========================================================================
%01)% PP AQ VT0 ===========================================================
% =========================================================================
comb_01_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_01_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13);  %N
comb_01_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14);  %Tx
comb_01_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15);                      %Ty
comb_01_u(1,5)=tab_AQ_utente(1,13);                                            %Tya
comb_01_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16); %Mx
% LATO MODENA =====================================================
comb_01_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17); %N
comb_01_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18); %Tx
comb_01_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19);                     %Ty
comb_01_u(1,10)=tab_AQ_utente(1,17);                                  %Tya
comb_01_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20);%Mx
    
% =========================================================================
%02)% PP AQ VT1 VT1A1 A110 FR01 ===========================================
% =========================================================================  
comb_02_u(1,1)=1; 
% LATO MANTOVA ====================================================
comb_02_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A1_utente(1,13)+tab_A110_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_02_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A1_utente(1,14)+tab_A110_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_02_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A1_utente(1,15)+tab_A110_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_02_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_02_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A1_utente(1,16)+tab_A110_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_02_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A1_utente(1,17)+tab_A110_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_02_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A1_utente(1,18)+tab_A110_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_02_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A1_utente(1,19)+tab_A110_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_02_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_02_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A1_utente(1,20)+tab_A110_utente(1,14)+tab_FR01_utente(1,12);   %Mx

% =========================================================================
%03)% PP AQ VT1 VT1A1 A110 FR02 ===========================================
% =========================================================================
comb_03_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_03_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A1_utente(1,13)+tab_A110_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_03_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A1_utente(1,14)+tab_A110_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_03_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A1_utente(1,15)+tab_A110_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_03_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_03_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A1_utente(1,16)+tab_A110_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_03_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A1_utente(1,17)+tab_A110_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_03_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A1_utente(1,18)+tab_A110_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_03_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A1_utente(1,19)+tab_A110_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_03_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_03_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A1_utente(1,20)+tab_A110_utente(1,14)+tab_FR02_utente(1,12);   %Mx
 
% =========================================================================
%04)% PP AQ VT1 VT1A1 A120 FR01 ===========================================
% =========================================================================
comb_04_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_04_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A1_utente(1,13)+tab_A120_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_04_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A1_utente(1,14)+tab_A120_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_04_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A1_utente(1,15)+tab_A120_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_04_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_04_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A1_utente(1,16)+tab_A120_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_04_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A1_utente(1,17)+tab_A120_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_04_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A1_utente(1,18)+tab_A120_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_04_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A1_utente(1,19)+tab_A120_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_04_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_04_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A1_utente(1,20)+tab_A120_utente(1,14)+tab_FR01_utente(1,12);   %Mx
    
% =========================================================================
%05)% PP AQ VT1 VT1A1 A120 FR02 ===========================================
% =========================================================================
comb_05_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_05_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A1_utente(1,13)+tab_A120_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_05_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A1_utente(1,14)+tab_A120_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_05_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A1_utente(1,15)+tab_A120_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_05_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_05_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A1_utente(1,16)+tab_A120_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_05_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A1_utente(1,17)+tab_A120_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_05_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A1_utente(1,18)+tab_A120_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_05_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A1_utente(1,19)+tab_A120_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_05_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_05_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A1_utente(1,20)+tab_A120_utente(1,14)+tab_FR02_utente(1,12);   %Mx
    
% =========================================================================
%06)% PP AQ VT1 VT1A2 A210 FR01 ===========================================
% =========================================================================
comb_06_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_06_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A2_utente(1,13)+tab_A210_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_06_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A2_utente(1,14)+tab_A210_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_06_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A2_utente(1,15)+tab_A210_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_06_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_06_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A2_utente(1,16)+tab_A210_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_06_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A2_utente(1,17)+tab_A210_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_06_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A2_utente(1,18)+tab_A210_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_06_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A2_utente(1,19)+tab_A210_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_06_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_06_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A2_utente(1,20)+tab_A210_utente(1,14)+tab_FR01_utente(1,12);   %Mx
    
% =========================================================================
%07)% PP AQ VT1 VT1A2 A210 FR02 ===========================================
% =========================================================================
comb_07_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_07_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A2_utente(1,13)+tab_A210_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_07_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A2_utente(1,14)+tab_A210_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_07_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A2_utente(1,15)+tab_A210_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_07_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_07_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A2_utente(1,16)+tab_A210_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_07_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A2_utente(1,17)+tab_A210_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_07_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A2_utente(1,18)+tab_A210_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_07_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A2_utente(1,19)+tab_A210_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_07_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_07_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A2_utente(1,20)+tab_A210_utente(1,14)+tab_FR02_utente(1,12);   %Mx
    
% =========================================================================
%08)% PP AQ VT1 VT1A2 A220 FR01 ===========================================
% =========================================================================
comb_08_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_08_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A2_utente(1,13)+tab_A220_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_08_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A2_utente(1,14)+tab_A220_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_08_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A2_utente(1,15)+tab_A220_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_08_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_08_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A2_utente(1,16)+tab_A220_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_08_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A2_utente(1,17)+tab_A220_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_08_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A2_utente(1,18)+tab_A220_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_08_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A2_utente(1,19)+tab_A220_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_08_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_08_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A2_utente(1,20)+tab_A220_utente(1,14)+tab_FR01_utente(1,12);   %Mx
    
% =========================================================================
%09)% PP AQ VT1 VT1A2 A220 FR02 ===========================================
% =========================================================================
comb_09_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_09_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A2_utente(1,13)+tab_A220_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_09_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A2_utente(1,14)+tab_A220_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_09_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A2_utente(1,15)+tab_A220_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_09_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_09_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A2_utente(1,16)+tab_A220_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_09_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A2_utente(1,17)+tab_A220_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_09_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A2_utente(1,18)+tab_A220_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_09_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A2_utente(1,19)+tab_A220_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_09_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_09_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A2_utente(1,20)+tab_A220_utente(1,14)+tab_FR02_utente(1,12);   %Mx
   
% =========================================================================
%10)% PP AQ VT1 VT1A3 A311 FR01 ===========================================
% =========================================================================
comb_10_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_10_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A311_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_10_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A311_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_10_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A311_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_10_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_10_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A311_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_10_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A311_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_10_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A311_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_10_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A311_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_10_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_10_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A311_utente(1,14)+tab_FR01_utente(1,12);   %Mx
    
% =========================================================================
%11)% PP AQ VT1 VT1A3 A311 FR02 ===========================================
% =========================================================================
comb_11_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_11_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A311_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_11_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A311_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_11_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A311_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_11_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_11_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A311_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_11_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A311_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_11_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A311_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_11_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A311_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_11_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_11_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A311_utente(1,14)+tab_FR02_utente(1,12);   %Mx
    
% =========================================================================
%12)% PP AQ VT1 VT1A3 A312 FR01 ===========================================
% =========================================================================
comb_12_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_12_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A312_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_12_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A312_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_12_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A312_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_12_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_12_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A312_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_12_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A312_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_12_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A312_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_12_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A312_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_12_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_12_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A312_utente(1,14)+tab_FR01_utente(1,12);   %Mx
   
% =========================================================================
%13)% PP AQ VT1 VT1A3 A312 FR02 ===========================================
% =========================================================================
comb_13_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_13_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A312_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_13_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A312_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_13_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A312_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_13_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_13_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A312_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_13_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A312_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_13_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A312_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_13_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A312_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_13_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_13_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A312_utente(1,14)+tab_FR02_utente(1,12);   %Mx
    
% =========================================================================
%14)% PP AQ VT1 VT1A3 A321 FR01 ===========================================
% =========================================================================
comb_14_u(1,1)=1;   
% LATO MANTOVA ====================================================
comb_14_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A321_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_14_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A321_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_14_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A321_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_14_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_14_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A321_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_14_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A321_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_14_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A321_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_14_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A321_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_14_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_14_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A321_utente(1,14)+tab_FR01_utente(1,12);   %Mx
  
% =========================================================================
%15)% PP AQ VT1 VT1A3 A321 FR02 ===========================================
% =========================================================================
comb_15_u(1,1)=1;    
% LATO MANTOVA ====================================================
comb_15_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A321_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_15_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A321_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_15_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A321_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_15_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_15_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A321_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_15_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A321_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_15_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A321_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_15_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A321_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_15_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_15_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A321_utente(1,14)+tab_FR02_utente(1,12);   %Mx
    
% =========================================================================
%16)% PP AQ VT1 VT1A3 A322 FR01 ===========================================
% =========================================================================
comb_16_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_16_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A322_utente(1,7)+tab_FR01_utente(1,5);       %N
comb_16_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A322_utente(1,8)+tab_FR01_utente(1,6);       %Tx
comb_16_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A322_utente(1,9)+tab_FR01_utente(1,7);                      %Ty
comb_16_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_16_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A322_utente(1,10)+tab_FR01_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_16_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A322_utente(1,11)+tab_FR01_utente(1,9);     %N
comb_16_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A322_utente(1,12)+tab_FR01_utente(1,10);    %Tx
comb_16_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A322_utente(1,13)+tab_FR01_utente(1,11);                   %Ty
comb_16_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_16_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A322_utente(1,14)+tab_FR01_utente(1,12);   %Mx
% =========================================================================
%17)% PP AQ VT1 VT1A3 A322 FR02 ===========================================
% =========================================================================
comb_17_u(1,1)=1;
% LATO MANTOVA ====================================================
comb_17_u(1,2)=tab_PP00_utente(1,7)+tab_AQ_utente(1,11)+tab_VT0_utente(1,13)+tab_VT1A3_utente(1,13)+tab_A322_utente(1,7)+tab_FR02_utente(1,5);       %N
comb_17_u(1,3)=tab_PP00_utente(1,8)+tab_AQ_utente(1,12)+tab_VT0_utente(1,14)+tab_VT1A3_utente(1,14)+tab_A322_utente(1,8)+tab_FR02_utente(1,6);       %Tx
comb_17_u(1,4)=tab_PP00_utente(1,9)+tab_VT0_utente(1,15)+tab_VT1A3_utente(1,15)+tab_A322_utente(1,9)+tab_FR02_utente(1,7);                      %Ty
comb_17_u(1,5)=tab_AQ_utente(1,13);                                                                              %Tya
comb_17_u(1,6)=tab_PP00_utente(1,10)+tab_AQ_utente(1,14)+tab_VT0_utente(1,16)+tab_VT1A3_utente(1,16)+tab_A322_utente(1,10)+tab_FR02_utente(1,8);     %Mx
% LATO MODENA =====================================================
comb_17_u(1,7)=tab_PP00_utente(1,11)+tab_AQ_utente(1,15)+tab_VT0_utente(1,17)+tab_VT1A3_utente(1,17)+tab_A322_utente(1,11)+tab_FR02_utente(1,9);     %N
comb_17_u(1,8)=tab_PP00_utente(1,12)+tab_AQ_utente(1,16)+tab_VT0_utente(1,18)+tab_VT1A3_utente(1,18)+tab_A322_utente(1,12)+tab_FR02_utente(1,10);    %Tx
comb_17_u(1,9)=tab_PP00_utente(1,13)+tab_VT0_utente(1,19)+tab_VT1A3_utente(1,19)+tab_A322_utente(1,13)+tab_FR02_utente(1,11);                   %Ty
comb_17_u(1,10)=tab_AQ_utente(1,17);                                                                             %Tya
comb_17_u(1,11)=tab_PP00_utente(1,14)+tab_AQ_utente(1,18)+tab_VT0_utente(1,20)+tab_VT1A3_utente(1,20)+tab_A322_utente(1,14)+tab_FR02_utente(1,12);   %Mx

global mat_comb_u;

% =========================================================================
% Matrice tridimensionale che raccoglie tutte le combinazioni (1-17) ======
mat_comb_u(:,:,1)=comb_01_u;  mat_comb_u(:,:,2)=comb_02_u; %===============
mat_comb_u(:,:,3)=comb_03_u;  mat_comb_u(:,:,4)=comb_04_u; %===============
mat_comb_u(:,:,5)=comb_05_u;  mat_comb_u(:,:,6)=comb_06_u; %===============
mat_comb_u(:,:,7)=comb_07_u;  mat_comb_u(:,:,8)=comb_08_u; %===============
mat_comb_u(:,:,9)=comb_09_u;  mat_comb_u(:,:,10)=comb_10_u; %==============
mat_comb_u(:,:,11)=comb_11_u; mat_comb_u(:,:,12)=comb_12_u; %==============
mat_comb_u(:,:,13)=comb_13_u; mat_comb_u(:,:,14)=comb_14_u; %==============
mat_comb_u(:,:,15)=comb_15_u; mat_comb_u(:,:,16)=comb_16_u; %==============
mat_comb_u(:,:,17)=comb_17_u; %============================================
% =========================================================================    
 global mat_comb_pali_u;
 global H1;   global H2;   global MH;   global H;
 
% parametri utili letti da txt modello
d=str2num(setup{18,1}); k=str2num(setup{22,1}); h1=str2num(setup{20,1});
    
for j=1:17
        % distanza che dipende dalla quota del fondo
        L2=str2num(setup{24,1})-tab_PP00_utente(1,2)+k;
        % ripartizione della spinta dell'acqua H in H1 e H2
        if tab_AQ_utente(1,2)>str2num(setup{24,1})
            h1aq=h1;
            if tab_TMA_utente(1,2)==0
                H=tab_AQ_utente(1,10)/2;
                MH=H*((tab_AQ_utente(1,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00_utente(1,2)+k);
            end
            if tab_TMA_utente(1,2)==1
                H=tab_AQ_utente(1,10)/2;
                MH=H*((tab_AQ_utente(1,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00_utente(1,2)+k);
            end
            H1=MH/(h1aq+L2);
            H2=H-H1;
        else
            h1aq=0;
            if tab_TMA_utente(1,2)==0
                H=tab_AQ_utente(1,10)/2;
                MH=H*((tab_AQ_utente(1,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00_utente(1,2)+k);
            end
            if tab_TMA_utente(1,2)==1
                H=tab_AQ_utente(1,10)/2;
                MH=H*((tab_AQ_utente(1,2)-str2num(setup{26,1}))/2+...
                str2num(setup{26,1})-tab_PP00_utente(1,2)+k);
            end
            H1=MH/(h1aq+L2);
            H2=H-H1;
        end
        mat_comb_pali_u(1,1,j)=1;
        % =================================================================
        % pali lato MANTOVA: P5, P3, P1 (da monte a valle) ================
        % =================================================================
        % palo P1 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali_u(1,2,j)=mat_comb_u(1,2,j)/3+mat_comb_u(1,6,j)/d+...
            mat_comb_u(1,4,j)/d*(h1+L2/2)+H1/d*(h1aq+L2/2);
        % palo P1 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,3,j)=mat_comb_u(1,3,j)/3;
        % palo P1 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,4,j)=mat_comb_u(1,4,j)/3+H/3;
        % palo P1 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,5,j)=mat_comb_u(1,4,j)*L2/6+H1*L2/6;
        % palo P1 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,6,j)=mat_comb_u(1,3,j)*(L2+h1)/3;
        % =================================================================
        % palo P3 - sollecitazione assiale N (N)
        mat_comb_pali_u(1,7,j)=mat_comb_u(1,2,j)/3;
        % palo P3 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,8,j)=mat_comb_u(1,3,j)/3;
        % palo P3 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,9,j)=mat_comb_u(1,4,j)/3+H/3;
        % palo P3 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,10,j)=mat_comb_u(1,4,j)*L2/6+H1*L2/6;
        % palo P3 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,11,j)=mat_comb_u(1,3,j)*(L2+h1)/3;
        % =====================================================================
        % palo P5 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali_u(1,12,j)=mat_comb_u(1,2,j)/3-mat_comb_u(1,6,j)/d-...
            mat_comb_u(1,4,j)/d*(h1+L2/2)-H1/d*(h1aq+L2/2);
        % palo P5 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,13,j)=mat_comb_u(1,3,j)/3;
        % palo P5 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,14,j)=mat_comb_u(1,4,j)/3+H/3;
        % palo P5 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,15,j)=mat_comb_u(1,4,j)*L2/6+H1*L2/6;
        % palo P5 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,16,j)=mat_comb_u(1,3,j)*(L2+h1)/3;
        % =================================================================
        % pali lato MODENA: P6, P4, P2 (da monte a valle) =================
        % =================================================================
        % palo P2 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali_u(1,17,j)=mat_comb_u(1,7,j)/3+mat_comb_u(1,11,j)/d+...
            mat_comb_u(1,9,j)/d*(h1+L2/2)+H1/d*(h1aq+L2/2);
        % palo P2 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,18,j)=mat_comb_u(1,8,j)/3;
        % palo P2 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,19,j)=mat_comb_u(1,9,j)/3+H/3;
        % palo P2 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,20,j)=mat_comb_u(1,9,j)*L2/6+H1*L2/6;
        % palo P2 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,21,j)=mat_comb_u(1,8,j)*(L2+h1)/3;
        % =================================================================
        % palo P4 - sollecitazione assiale N (N)
        mat_comb_pali_u(1,22,j)=mat_comb_u(1,7,j)/3;
        % palo P4 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,23,j)=mat_comb_u(1,8,j)/3;
        % palo P4 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,24,j)=mat_comb_u(1,9,j)/3+H/3;
        % palo P4 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,25,j)=mat_comb_u(1,9,j)*L2/6+H1*L2/6;
        % palo P4 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,26,j)=mat_comb_u(1,8,j)*(L2+h1)/3;
        % =================================================================
        % palo P6 - sollecitazione assiale N (N, Mx, Ty, Tya)
        mat_comb_pali_u(1,27,j)=mat_comb_u(1,7,j)/3-mat_comb_u(1,11,j)/d-...
            mat_comb_u(1,9,j)/d*(h1+L2/2)-H1/d*(h1aq+L2/2);
        % palo P6 - sollecitazione tagliante Tx (Tx)
        mat_comb_pali_u(1,28,j)=mat_comb_u(1,8,j)/3;
        % palo P6 - sollecitazione tagliante Ty (Ty, Tya)
        mat_comb_pali_u(1,29,j)=mat_comb_u(1,9,j)/3+H/3;
        % palo P6 - sollecitazione flettente Mx (Ty, Tya)
        mat_comb_pali_u(1,30,j)=mat_comb_u(1,9,j)*L2/6+H1*L2/6;
        % palo P6 - sollecitazione flettente My (Tx)
        mat_comb_pali_u(1,31,j)=mat_comb_u(1,8,j)*(L2+h1)/3;
end

% =========================================================================
global mat_comb_carichiT_ecc_u;
for j=1:17
        mat_comb_carichiT_ecc_u(1,1,j)=1;
        % palo P1 (Lato Mantova, Valle)====================================
        mat_comb_carichiT_ecc_u(1,2,j)=mat_comb_pali_u(1,2,j);               %N
        mat_comb_carichiT_ecc_u(1,3,j)=sqrt(mat_comb_pali_u(1,3,j)^2+mat_comb_pali_u(1,4,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,4,j)=sqrt(mat_comb_pali_u(1,5,j)^2+mat_comb_pali_u(1,6,j)^2);       %Mtot
        mat_comb_carichiT_ecc_u(1,5,j)=mat_comb_carichiT_ecc_u(1,4,j)/mat_comb_carichiT_ecc_u(1,2,j); %e=Mtot/N
        % palo P3 (Lato Mantova, Centro)===================================
        mat_comb_carichiT_ecc_u(1,6,j)=mat_comb_pali_u(1,7,j);               %N
        mat_comb_carichiT_ecc_u(1,7,j)=sqrt(mat_comb_pali_u(1,8,j)^2+mat_comb_pali_u(1,9,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,8,j)=sqrt(mat_comb_pali_u(1,10,j)^2+mat_comb_pali_u(1,11,j)^2);     %Ttot
        mat_comb_carichiT_ecc_u(1,9,j)=mat_comb_carichiT_ecc_u(1,8,j)/mat_comb_carichiT_ecc_u(1,6,j); %e=Mtot/N
        % palo P5 (Lato Mantova, Monte)====================================
        mat_comb_carichiT_ecc_u(1,10,j)=mat_comb_pali_u(1,12,j);             %N
        mat_comb_carichiT_ecc_u(1,11,j)=sqrt(mat_comb_pali_u(1,13,j)^2+mat_comb_pali_u(1,14,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,12,j)=sqrt(mat_comb_pali_u(1,15,j)^2+mat_comb_pali_u(1,16,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,13,j)=mat_comb_carichiT_ecc_u(1,12,j)/mat_comb_carichiT_ecc_u(1,10,j); %e=Mtot/N
        % palo P2 (Lato Modena, Valle)=====================================
        mat_comb_carichiT_ecc_u(1,14,j)=mat_comb_pali_u(1,17,j);             %N
        mat_comb_carichiT_ecc_u(1,15,j)=sqrt(mat_comb_pali_u(1,18,j)^2+mat_comb_pali_u(1,19,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,16,j)=sqrt(mat_comb_pali_u(1,20,j)^2+mat_comb_pali_u(1,21,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,17,j)=mat_comb_carichiT_ecc_u(1,16,j)/mat_comb_carichiT_ecc_u(1,14,j); %e=Mtot/N
        % palo P4 (Lato Modena, Centro)====================================
        mat_comb_carichiT_ecc_u(1,18,j)=mat_comb_pali_u(1,22,j);             %N
        mat_comb_carichiT_ecc_u(1,19,j)=sqrt(mat_comb_pali_u(1,23,j)^2+mat_comb_pali_u(1,24,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,20,j)=sqrt(mat_comb_pali_u(1,25,j)^2+mat_comb_pali_u(1,26,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,21,j)=mat_comb_carichiT_ecc_u(1,20,j)/mat_comb_carichiT_ecc_u(1,18,j); %e=Mtot/N
        % palo P6 (Lato Modena, Monte)=====================================
        mat_comb_carichiT_ecc_u(1,22,j)=mat_comb_pali_u(1,27,j);             %N
        mat_comb_carichiT_ecc_u(1,23,j)=sqrt(mat_comb_pali_u(1,28,j)^2+mat_comb_pali_u(1,29,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,24,j)=sqrt(mat_comb_pali_u(1,30,j)^2+mat_comb_pali_u(1,31,j)^2);       %Ttot
        mat_comb_carichiT_ecc_u(1,25,j)=mat_comb_carichiT_ecc_u(1,24,j)/mat_comb_carichiT_ecc_u(1,22,j); %e=Mtot/N
end

% acquisizione dei parametri utili per le verifiche di pressoflessione
Rgi=str2num(setup{36,1});     % raggio giratore di inerzia
Rcls=str2num(setup{28,1});    % raggio palo cls
R1=str2num(setup{30,1});      % raggio posizionamento armatura
Dferro=str2num(setup{32,1});  % diametro barra acciaio
num=str2num(setup{34,1});     % numero delle barre
n=str2num(setup{38,1});       % n=15 rapporto moduli elastici


Acls=pi()*Rcls^2;                   % area della sezione del palo in cls
Aferrotot=num*(pi()*Dferro^2)/4;    % area totale dell'armatura
Aomog=Acls+n*Aferrotot;             % area omogeneizzata
Jcls=pi()*Rcls^4/4;                 % mom. inerzia area cls

a_cls=str2num(setup{43,1});      b_cls=str2num(setup{45,1});      
c_cls=str2num(setup{47,1});      d_cls=str2num(setup{49,1});      
f_cls=str2num(setup{51,1});

a_acc=str2num(setup{56,1});      b_acc=str2num(setup{58,1});      
c_acc=str2num(setup{60,1});      d_acc=str2num(setup{62,1});      
f_acc=str2num(setup{64,1});

global mat_comb_carichiT_sigma_u;
for j=1:17
        mat_comb_carichiT_sigma_u(1,1,j)=1;
        c_u=0;
        for v=5:4:25
            % CASO PICCOLA ECCENTRICITA' ==================================
            % sezione completamente reagente, l'acciaio non lavora....
            % =============================================================
            if mat_comb_carichiT_ecc_u(1,v,j)<=Rgi % piccola eccentricità
                % generico Palo ===========================================
                mat_comb_carichiT_sigma_u(1,v-(3+c_u),j)=mat_comb_carichiT_ecc_u(1,v-3,j)/1000/Aomog+...
                    mat_comb_carichiT_ecc_u(1,v-3,j)*mat_comb_carichiT_ecc_u(1,v,j)/1000/Jcls*Rcls;
                mat_comb_carichiT_sigma_u(1,v-(3+c_u)+1,j)=0;
            % CASO GRANDE ECCENTRICITA' ===================================
            % sezione parzializzata, l'acciaio lavora....
            % =============================================================
            else      % grande eccentricità
                % generico Palo ===========================================
                ecc=mat_comb_carichiT_ecc_u(1,v,j);
                mat_comb_carichiT_sigma_u(1,v-(3+c_u),j)=mat_comb_carichiT_ecc_u(1,v-3,j)/1000*...
                    (a_cls*ecc^4+b_cls*ecc^3+c_cls*ecc^2+d_cls*ecc+f_cls);
                mat_comb_carichiT_sigma_u(1,v-(3+c_u)+1,j)=mat_comb_carichiT_ecc_u(1,v-3,j)/1000*...
                    (a_acc*ecc^4+b_acc*ecc^3+c_acc*ecc^2+d_acc*ecc+f_acc);
            end
            c_u=c_u+2;
        end
end

global set_verifica;
cls_amm=str2num(setup{169,1});      cls_lim=str2num(setup{171,1});      
acc_amm=str2num(setup{176,1});      acc_lim=str2num(setup{178,1});

global mat_comb_carichiT_CS_u;

for j=1:17
    mat_comb_carichiT_CS_u(1,1,j)=1;
    for i=2:2:12
        mat_comb_carichiT_CS_u(1,i,j)=cls_amm/mat_comb_carichiT_sigma_u(1,i,j);
        mat_comb_carichiT_CS_u(1,i+1,j)=acc_amm/mat_comb_carichiT_sigma_u(1,i+1,j);
    end
end


global provvisorio;
for i=2:13
    provvisorio(1,i-1)=min(mat_comb_carichiT_CS_u(1,i,:));    
end

global mat_CS_maxcomb_u;  
mat_CS_maxcomb_u=min(provvisorio(1,:));

global CS_orack_ok;
CS_orack_str=num2str(CS_orack_ok);

mat_CS_maxcomb_u_chop=chop(mat_CS_maxcomb_u,3);
mat_CS_maxcomb_u_chop_str=num2str(mat_CS_maxcomb_u_chop);

adesso_CS_futuro={CS_orack_str,mat_CS_maxcomb_u_chop_str};
set(handles.uitable7,'data',adesso_CS_futuro);

% 
% 
%     
% 
% 
% % --- Executes on selection change in popupmenu3.
% function popupmenu3_Callback(hObject, eventdata, handles)
% % hObject    handle to popupmenu3 (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    structure with handles and user data (see GUIDATA)
% 
% % Hints: contents = get(hObject,'String') returns popupmenu3 contents as cell array
% %        contents{get(hObject,'Value')} returns selected item from popupmenu3
% 
% 
% % --- Executes during object creation, after setting all properties.
% function popupmenu3_CreateFcn(hObject, eventdata, handles)
% % hObject    handle to popupmenu3 (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    empty - handles not created until after all CreateFcns called
% 
% % Hint: popupmenu controls usually have a white background on Windows.
% %       See ISPC and COMPUTER.
% if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
%     set(hObject,'BackgroundColor','white');
% end
% 
% 
% % --- Executes on selection change in popupmenu4.
% function popupmenu4_Callback(hObject, eventdata, handles)
% % hObject    handle to popupmenu4 (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    structure with handles and user data (see GUIDATA)
% 
% % Hints: contents = get(hObject,'String') returns popupmenu4 contents as cell array
% %        contents{get(hObject,'Value')} returns selected item from popupmenu4
% 
% 
% % --- Executes during object creation, after setting all properties.
% function popupmenu4_CreateFcn(hObject, eventdata, handles)
% % hObject    handle to popupmenu4 (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    empty - handles not created until after all CreateFcns called
% 
% % Hint: popupmenu controls usually have a white background on Windows.
% %       See ISPC and COMPUTER.
% if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
%     set(hObject,'BackgroundColor','white');
% end
% 
% 




% % SMISTAMENTO FILE ========================================================
% % in questo blocco si trasferiscono i file dalla cartella archivio alle
% % cartelle di destinazione:
% % LIMBO: i file che devono essere analizzati
% % ARCHIVIO: file che non sono da analizzare ("file vecchi")
% % l'elenco di tutti i file è stato creato attraverso delle matrici, una per
% % ogni tipoligia di file:
% % >>>   mat_check_link;
% % >>>   mat_telma_link;
% % >>>   mat_telmo_link;
% % >>>   mat_aneidro_link;
% % >>>   mat_sonar_link;
% % in queste matrici c'è una cololla, l'ultima, apposirìtamente creata per
% % distinguere se il generico file è da LIMBO o da ARCHIVIO
% % >>>   100 --> LIMBO
% % >>>   200 --> ARCHIVIO
% 
% % --- Executes on button press in pushbutton7.
% function pushbutton7_Callback(hObject, eventdata, handles)
% % hObject    handle to pushbutton7 (see GCBO)
% % eventdata  reserved - to be defined in a future version of MATLAB
% % handles    structure with handles and user data (see GUIDATA)
% 
% global limbo; global archivio;
% 
% global mat_check_link;   global mat_telma_link; global mat_telmo_link;
% global mat_aneidro_link; global mat_sonar_link;
% 
% global limbo_aneidro; global limbo_sonar; global limbo_check;
% global limbo_telma;   global limbo_telmo;
% 
% global archivio_aneidro; global archivio_sonar; global archivio_check;
% global archivio_telma;   global archivio_telmo; global archivio_sospese;
% 
% global nomecheck;
% 
% global st_ck_limbo;      global st_ck_archivio;
% global st_telma_limbo;   global st_telma_archivio;
% global st_telmo_limbo;   global st_telmo_archivio;
% global st_aneidro_limbo; global st_aneidro_archivio;
% global st_sonar_limbo;   global st_sonar_archivio;
% 
% global table;
% 
% %==========================================================================
% % CHECK ===================================================================
% % contatore di file spostati nelle due cartelle per avere un check
% 
% barra=waitbar(0,'1/6: smistamento file di check');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[1 0 0],'FaceColor',[1 0 0]) % barra rossa
% steps=size(mat_check_link,1);
% 
% st_ck_limbo=0; st_ck_archivio=0;
% 
% for i=1:size(mat_check_link,1)
%     if mat_check_link(i,5)==100
%         ggck=num2str(mat_check_link(i,2));
%         mmck=num2str(mat_check_link(i,3));
%         aack=num2str(mat_check_link(i,4)-2000);
%         nomecheck=strcat('router',mmck,'-',ggck,'-',aack,'.txt');
%         [status,message,messageid]=movefile(nomecheck,limbo_check);
%         if status==1
%             st_ck_limbo=st_ck_limbo+1;
%         end
%         
%     else
%         ggck=num2str(mat_check_link(i,2));
%         mmck=num2str(mat_check_link(i,3));
%         aack=num2str(mat_check_link(i,4)-2000);
%         nomecheck=strcat('router',mmck,'-',ggck,'-',aack,'.txt');
%         [status,message,messageid]=movefile(nomecheck,archivio_check);
%         if status==1
%             st_ck_archivio=st_ck_archivio+1;
%         end
%     end
%     waitbar(i/steps);
% end
% close(barra);
% %==========================================================================
% %==========================================================================
% 
% 
% %==========================================================================
% % TELECAMERA MANTOVA ======================================================
% % contatore di file spostati nelle due cartelle per avere un check
% 
% barra=waitbar(0,'2/6: smistamento file telecamera Mantova');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[0 1 1],'FaceColor',[0 1 1]) % barra ciano
% steps=size(mat_telma_link,1);
% 
% st_telma_limbo=0; st_telma_archivio=0;
% 
% for i=1:size(mat_telma_link,1)
%     if mat_telma_link(i,8)==100
%         datama=num2str(mat_telma_link(i,1));
%         nometelma=strcat('mantova',datama,'.jpg');
%         [status,message,messageid]=movefile(nometelma,limbo_telma);
%         if status==1
%             st_telma_limbo=st_telma_limbo+1;
%         end
%     else
%         datama=num2str(mat_telma_link(i,1));
%         nometelma=strcat('mantova',datama,'.jpg');
%         [status,message,messageid]=movefile(nometelma,archivio_telma);
%         if status==1
%             st_telma_archivio=st_telma_archivio+1;
%         end
%     end
%     waitbar(i/steps);
% end
% close(barra);
% %==========================================================================
% %==========================================================================
% 
% %==========================================================================
% % TELECAMERA MODENA =======================================================
% % contatore di file spostati nelle due cartelle per avere un check
% 
% barra=waitbar(0,'3/6: smistamento file telecamera Modena');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[1 0 1],'FaceColor',[1 0 1]) % barra magenta
% steps=size(mat_telmo_link,1);
% 
% st_telmo_limbo=0; st_telmo_archivio=0;
% 
% for i=1:size(mat_telmo_link,1)
%     if mat_telmo_link(i,8)==100
%         datamo=num2str(mat_telmo_link(i,1));
%         nometelmo=strcat('modena',datamo,'.jpg');
%         [status,message,messageid]=movefile(nometelmo,limbo_telmo);
%         if status==1
%             st_telmo_limbo=st_telmo_limbo+1;
%         end
%     else
%         datamo=num2str(mat_telmo_link(i,1));
%         nometelmo=strcat('modena',datamo,'.jpg');
%         [status,message,messageid]=movefile(nometelmo,archivio_telmo);
%         if status==1
%             st_telmo_archivio=st_telmo_archivio+1;
%         end
%     end
%     waitbar(i/steps);
% end
% close(barra);
% %==========================================================================
% %==========================================================================
% 
% %==========================================================================
% % ANEMOMETRO E IDROMETRO ==================================================
% % contatore di file spostati nelle due cartelle per avere un check
% 
% barra=waitbar(0,'4/6: smistamento file anemometro e idrometro');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[1 1 0],'FaceColor',[1 1 0]) % barra gialla
% steps=size(mat_aneidro_link,1);
% 
% st_aneidro_limbo=0; st_aneidro_archivio=0;
% 
% for i=1:size(mat_aneidro_link,1)
%     if mat_aneidro_link(i,8)==100
%         datamo=num2str(mat_aneidro_link(i,1));
%         nomeaneidro=strcat('analog',datamo,'.txt');
%         [status,message,messageid]=movefile(nomeaneidro,limbo_aneidro);
%         if status==1
%             st_aneidro_limbo=st_aneidro_limbo+1;
%         end
%     else
%         datamo=num2str(mat_aneidro_link(i,1));
%         nomeaneidro=strcat('analog',datamo,'.txt');
%         [status,message,messageid]=movefile(nomeaneidro,archivio_aneidro);
%         if status==1
%             st_aneidro_archivio=st_aneidro_archivio+1;
%         end
%     end
%     waitbar(i/steps);
% end
% close(barra);
% %==========================================================================
% %==========================================================================
% 
% %==========================================================================
% % SONAR ===================================================================
% % contatore di file spostati nelle due cartelle per avere un check
% 
% barra=waitbar(0,'5/6: smistamento file sonar');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[0 0 1],'FaceColor',[0 0 1]) % barra blu
% steps=size(mat_sonar_link,1);
% 
% st_sonar_limbo=0; st_sonar_archivio=0;
% 
% for i=1:size(mat_sonar_link,1)
%     if mat_sonar_link(i,8)==100
%         datamo=num2str(mat_sonar_link(i,1));
%         nomesonar=strcat('sonar',datamo,'.txt');
%         [status,message,messageid]=movefile(nomesonar,limbo_sonar);
%         if status==1
%             st_sonar_limbo=st_sonar_limbo+1;
%         end
%     else
%         datamo=num2str(mat_sonar_link(i,1));
%         nomesonar=strcat('sonar',datamo,'.txt');
%         [status,message,messageid]=movefile(nomesonar,archivio_sonar);
%         if status==1
%             st_sonar_archivio=st_sonar_archivio+1;
%         end
%     end
%     waitbar(i/steps);
% end
% close(barra);
% %==========================================================================
% %==========================================================================
% % Alla fine del trasferimento nella cartella di arrivo rimangono solo gli
% % eventuali file che sono stati eliminati dall'elenco, ovverosia quelli che
% % per il passaggio dall'ora legale all'ora solare (ottobre) anche se sono
% % stati acquisiti in due tempi assoluti differenti coincidono.....
% 
% cercasospese=dir;
% st_sospese_archivio=0;
% 
% barra=waitbar(0,'6/6: smistamento altri file');
% hw=findobj(barra,'Type','Patch');
% set(hw,'EdgeColor',[0 0 0],'FaceColor',[0 0 0]) % barra nera
% steps=size(cercasospese,1)-2;
% 
% for i=3:size(cercasospese,1)
%     nomesospese=cercasospese(i).name;
%     [status,message,messageid]=movefile(nomesospese,archivio_sospese);
%     if status==1
%         st_sospese_archivio=st_sospese_archivio+1;
%     end
%     waitbar((i-2)/steps);
% end
% close(barra);
% 
% % Controllo sul trasferimento file nelle varie cartelle. Durante il
% % trasferimento file è stato conteggiato per ogni tipologia di file il
% % numero di file che sono stati spostati in LIMBO e in ARCHIVIO utilizzando
% % lo status restituito dalla funzione MOVEFILE. Il check viene fatto sulla
% % somma di questi indici con il totale dei vari file....
% 
% if st_ck_limbo+st_ck_archivio==table(2,6)
%     if st_telma_limbo+st_telma_archivio==table(2,4)
%         if st_telmo_limbo+st_telmo_archivio==table(2,5)
%             if st_aneidro_limbo+st_aneidro_archivio==table(2,1)
%                 if st_sonar_limbo+st_sonar_archivio==table(2,3)
%                     if st_sospese_archivio==(size(cercasospese,1)-2)
%                         messaggiocheck='ok, smistamento completato con successo';
%                     else
%                         messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
%                     end
%                 else
%                     messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
%                 end
%             else
%                 messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
%             end
%         else
%             messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
%         end
%     else
%         messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
%     end
% else
%     messaggiocheck='!!!ATTENZIONE!!! qualcosa è andato storto';
% end
% 
% set(handles.text11,'string',messaggiocheck);
% %==========================================================================
% %==========================================================================
% %==========================================================================
% %==========================================================================
% 
































% PEZZI gia ricompilati, lascio traccia del vecchio


% =========================================================================
% ANEMOMETRO E IDROMETRO ==================================================

% indice=1; % riduzione matrice link ai soli file spostati "100"
% for i=1:size(mat_aneidro_link,1)
%     if mat_aneidro_link(i,8)==100
%         for j=1:7
%         matrice_ridotta_aneidro(indice,j)=mat_aneidro_link(i,j);
%         end
%         indice=indice+1;
%     end
% end
% % riduzione della matrice_ridotta_aneidro ai soli file utili (no 0 bytes)
% for i=1:size(matrice_ridotta_aneidro,1)
%     stringafile=num2str(matrice_ridotta_aneidro(i,1));
%     nomefile=strcat('analog',stringafile,'.txt');
%     for j=1:size(eee,1)
%         if nomefile==eee(j).name
%             if eee(j).bytes>0
%                 matrice_analisi_aneidro(j,1)=matrice_ridotta_aneidro(i,1);
%                 matrice_analisi_aneidro(j,2)=matrice_ridotta_aneidro(i,2);
%                 matrice_analisi_aneidro(j,3)=matrice_ridotta_aneidro(i,3);
%                 matrice_analisi_aneidro(j,4)=matrice_ridotta_aneidro(i,4);
%                 matrice_analisi_aneidro(j,5)=matrice_ridotta_aneidro(i,5);
%                 matrice_analisi_aneidro(j,6)=matrice_ridotta_aneidro(i,6);
%                 matrice_analisi_aneidro(j,7)=matrice_ridotta_aneidro(i,7);
%             end
%         end
%     end
% end
% % estrazione dei dati e conversione, il file ameidro ha 4 colonne:
% % 1) colonna anemometro velocità
% % 2) colonna idrometro
% % 3) colonna anemometro direzione
% % 4) tempo di acquisizione
% x=1;
% for i=1:size(eee,1)
%     % con la funzione textread leggo il primo file e creo una matrice che
%     % ha una sola colonna. I valori nelle righe rappresentano a seconda
%     % della riga una delle 4 informazioni riportate sopra....
%     % riga 1: anemometro velocità
%     % riga 2: idrometro
%     % riga 3: anemometro direzione
%     % riga 4: tempo di acquisizione
%     % ricomnicio....
%     % riga 5: anemometro velocità
%     % riga 6: così via....
%     z=textread(eee(i).name,'%n');
%     h=1;
%     % ciclo per leggere i file di z e creare una matrice in cui i diversi
%     % valori siano in colonne differenti....
%     for j=1:4:size(z,1)-3
%         % prima riga e poi multiple di 4....anemometro velocità
%         tab(h,1)=((z(j,1)*1000)-4)*3.75;
%         % terza riga e poi multiple di 4....anemometro direzione
%         tab(h,2)=((z(j+2,1)*1000)-4)*22.5;
%         % seconda riga e poi multiple di 4....idrometro
%         tab(h,3)=(29.86-(20+((z(j+1,1)*1000)-4)*(-1.28)));
%         % quarta riga e poi multiple di 4....tempo acquisizione
%         tab(h,4)=z(j+3,1)-z(4,1);
%         h=h+1;
%     end
%     % alla fine di questo ciclo for ho una matrice con 4 colonne
%     %  vel_ane     dir_ane     idrometro      tempo
%     %   ....        ....         .....         ...
%     % realizzazione della tabella medie orarie (servono per i calcoli)
%     tab_medie(i,1)=mean(tab(:,1),1); % anemometro velocità
%     tab_medie(i,2)=mean(tab(:,2),1); % anemometro direzione
%     tab_medie(i,3)=mean(tab(:,3),1); % idrometro
%     tab_medie(i,4)=matrice_analisi_aneidro(i,2);
%     tab_medie(i,5)=matrice_analisi_aneidro(i,3);
%     tab_medie(i,6)=matrice_analisi_aneidro(i,4);
%     tab_medie(i,7)=matrice_analisi_aneidro(i,5);
%     tab_medie(i,8)=matrice_analisi_aneidro(i,6);
%     tab_medie(i,9)=matrice_analisi_aneidro(i,7);
%     % realizzazione della tabella dei massimi e direzione associata
%     tab_max(i,1)=max(tab(:,1));
%     for o=1:size(tab,1)
%         if tab(o,1)==tab_max(i,1)
%             tab_max(i,2)=tab(o,2);
%         end
%     end
%     % realizzazione della tabella varianze orarie
%     tab_var(i,1)=var(tab(:,1),1); % anemometro velocità
%     tab_var(i,2)=var(tab(:,2),1); % anemometro direzione
%     tab_var(i,3)=var(tab(:,3),1); % idrometro
%     tab_var(i,4)=matrice_analisi_aneidro(i,2);
%     tab_var(i,5)=matrice_analisi_aneidro(i,3);
%     tab_var(i,6)=matrice_analisi_aneidro(i,4);
%     tab_var(i,7)=matrice_analisi_aneidro(i,5);
%     tab_var(i,8)=matrice_analisi_aneidro(i,6);
%     tab_var(i,9)=matrice_analisi_aneidro(i,7);
%     % realizzare medie/var/max per il vento ogni 10 min secondo normativa 
%     % per una visualizzazione dell'andamento del vento.....
%     
%     % 1A colonna: numerazione progressiva
%     tab_vento(x,1) = i;
%     tab_vento(x+1,1)=i+0.166;
%     tab_vento(x+2,1)=i+0.333;
%     tab_vento(x+3,1)=i+0.5;
%     tab_vento(x+4,1)=i+0.666;
%     tab_vento(x+5,1)=i+0.833;
%     % 2A colonna: media/10 min velocità vento
%     tab_vento(x,2) = mean(tab(1:600,1),1);
%     tab_vento(x+1,2)=mean(tab(601:1200,1),1);
%     tab_vento(x+2,2)=mean(tab(1201:1800,1),1);
%     tab_vento(x+3,2)=mean(tab(1801:2400,1),1);
%     tab_vento(x+4,2)=mean(tab(2401:3000,1),1);
%     tab_vento(x+5,2)=mean(tab(3001:3600,1),1);
%     % 3A colonna: media/10 min direzione vento
%     tab_vento(x,3) = mean(tab(1:600,2),1);
%     tab_vento(x+1,3)=mean(tab(601:1200,2),1);
%     tab_vento(x+2,3)=mean(tab(1201:1800,2),1);
%     tab_vento(x+3,3)=mean(tab(1801:2400,2),1);
%     tab_vento(x+4,3)=mean(tab(2401:3000,2),1);
%     tab_vento(x+5,3)=mean(tab(3001:3600,2),1);
%     % 4A colonna: varianza/10 min direzione vento
%     tab_vento(x,4)=var(tab(1:600,1),1);
%     tab_vento(x+1,4)=var(tab(601:1200,1),1);
%     tab_vento(x+2,4)=var(tab(1201:1800,1),1);
%     tab_vento(x+3,4)=var(tab(1801:2400,1),1);
%     tab_vento(x+4,4)=var(tab(2401:3000,1),1);
%     tab_vento(x+5,4)=var(tab(3001:3600,1),1);
%     % 5A colonna: max/10 min direzione vento (folata)
%     tab_vento(x,5) = max(tab(1:600,1));
%     tab_vento(x+1,5)=max(tab(601:1200,1));
%     tab_vento(x+2,5)=max(tab(1201:1800,1));
%     tab_vento(x+3,5)=max(tab(1801:2400,1));
%     tab_vento(x+4,5)=max(tab(2401:3000,1));
%     tab_vento(x+5,5)=max(tab(3001:3600,1));
%     % 6A colonna: direzione associata al max(folata)
%     for k=1:size(tab,1)
%         if tab_vento(x,5)==tab(k,1)
%             tab_vento(x,6)=tab(k,2);
%         end
%         if tab_vento(x+1,5)==tab(k,1)
%             tab_vento(x+1,6)=tab(k,2);
%         end
%         if tab_vento(x+2,5)==tab(k,1)
%             tab_vento(x+2,6)=tab(k,2);
%         end
%         if tab_vento(x+3,5)==tab(k,1)
%             tab_vento(x+3,6)=tab(k,2);
%         end
%         if tab_vento(x+4,5)==tab(k,1)
%             tab_vento(x+4,6)=tab(k,2);
%         end
%         if tab_vento(x+5,5)==tab(k,1)
%             tab_vento(x+5,6)=tab(k,2);
%         end
%     end
%     x=x+6;
% end
% 
% for i=1:size(matrice_analisi_aneidro,1)
%     timing=matrice_analisi_aneidro(i,5)+matrice_analisi_aneidro(i,6)/60+...
%     matrice_analisi_aneidro(i,7)/3600;
%     tab_vento((1+(i-1)*6),7)=timing;
% end
% 
% for i=1:6:(size(tab_vento,1)-5)
%     tab_vento(i+1,7)=tab_vento(i,7)+0.166;
%     tab_vento(i+2,7)=tab_vento(i,7)+0.333;
%     tab_vento(i+3,7)=tab_vento(i,7)+0.5;
%     tab_vento(i+4,7)=tab_vento(i,7)+0.666;
%     tab_vento(i+5,7)=tab_vento(i,7)+0.833;
% end
% 
% for i=1:size(tab_medie,1)
%     tab_idro(i,1)=tab_medie(i,3);
%     tab_idro(i,2)=tab_var(i,3);
% end
% 
% % matrice riassuntiva anemometro (tab_ane)
% for i=1:size(tab_medie,1)
%     tab_ane(i,1)=tab_medie(i,1); % velocità media vento (1 ora)
%     tab_ane(i,2)=tab_medie(i,2); % direzione media vento (1 ora)
%     tab_ane(i,3)=tab_var(i,1); % varianza velocità (1 ora)
%     tab_ane(i,4)=tab_var(i,2); % varianza direzione (1 ora)
%     tab_ane(i,5)=tab_max(i,1); % velocità max (1 ora)
%     tab_ane(i,6)=tab_max(i,2);  % direzione associata a Vmax
% end
% 
% % MATRICE_TOTALE_ANE ======================================================
% for i=1:size(tab_ane,1)
%     for j=1:14
%         if j<=8
%             if j==8
%                 matrice_totale_ane(i,j)=matrice_analisi_aneidro(i,5)+...
%                 matrice_analisi_aneidro(i,6)/60+...
%                 matrice_analisi_aneidro(i,7)/3600;
%             else
%                 matrice_totale_ane(i,j)=matrice_analisi_aneidro(i,j);
%             end
%         end
%         if j>8
%             matrice_totale_ane(i,j)=tab_ane(i,j-8);
%         end
%     end
% end
% % MATRICE_TOTALE_IDRO =====================================================
% for i=1:size(tab_idro,1)
%     for j=1:10
%         if j<=8
%             if j==8
%                 matrice_totale_idro(i,j)=matrice_analisi_aneidro(i,5)+...
%                 matrice_analisi_aneidro(i,6)/60+...
%                 matrice_analisi_aneidro(i,7)/3600;
%             else
%                 matrice_totale_idro(i,j)=matrice_analisi_aneidro(i,j);
%             end
%         end
%         if j>8
%             matrice_totale_idro(i,j)=tab_idro(i,j-8);
%         end
%     end
% end
% 
% 
% 
% % eliminazione delle matrici e,ee,eee in quanto riutilizzate in seguito    
% clear e; clear ee; clear eee;
% 
% %==========================================================================
% % SONAR ===================================================================
% % 1) matrice dir 
% % 2) eliminazione dei primi di file "." ".."
% % 3) eliminazione dei file zero (riavvio)
% % 4) recupero informazioni
% % 5) creazione matrice analisi
% 
% 
% cd(limbo_sonar); % posizionamento nella cartella di riferimento
% e=dir; % elenco dei file spostati nella cartella
% for i=3:size(e,1) % eliminazione dei primi due ("." "..")
%     ee(i-2,1)=e(i,1);
% end
% indice=1;
% for i=1:size(ee,1) % eliminazione dei file 0 bytes (riavvio sistema)
%     if ee(i).bytes >0
%     eee(indice,1)=ee(i,1); % eee contiene l'elenco dei file da elaborare
%     indice=indice+1;
%     end
% end
% indice=1;
% for i=1:size(mat_sonar_link,1) % riduzione matrice link ai file "100"
%     if mat_sonar_link(i,8)==100
%         for j=1:7
%         matrice_ridotta_sonar(indice,j)=mat_sonar_link(i,j);
%         end
%         indice=indice+1;
%     end
% end
% % riduzione della matrice_ridotta_sonar ai soli file utili (no 0 bytes)
% for i=1:size(matrice_ridotta_sonar,1)
%     stringafile=num2str(matrice_ridotta_sonar(i,1));
%     nomefile=strcat('sonar',stringafile,'.txt');
%     for j=1:size(eee,1)
%         if nomefile==eee(j).name
%             if eee(j).bytes>0
%                 matrice_analisi_sonar(j,1)=matrice_ridotta_sonar(i,1);
%                 matrice_analisi_sonar(j,2)=matrice_ridotta_sonar(i,2);
%                 matrice_analisi_sonar(j,3)=matrice_ridotta_sonar(i,3);
%                 matrice_analisi_sonar(j,4)=matrice_ridotta_sonar(i,4);
%                 matrice_analisi_sonar(j,5)=matrice_ridotta_sonar(i,5);
%                 matrice_analisi_sonar(j,6)=matrice_ridotta_sonar(i,6);
%                 matrice_analisi_sonar(j,7)=matrice_ridotta_sonar(i,7);
%             end
%         end
%     end
% end
% 
% k=1;
% h=1;
% E=0;
% nodato=0;
% 
% % estrazione dei dati e conversione
% for i=1:size(eee,1)
%     numero=0;
%     % legge uno per volta i file contenuti nell'elenco
%     z=textread(eee(i).name,'%s');
%     % questa tipologia di lettura crea un matrice con una sola colonna in
%     % cui sono elencati in sequenza i numeri letti nel txt, ovverosia
%     % alcuni sono i dati del sonar altri sono le codifiche tempo labview
%     for j=1:size(z,1)
%         % leggo il primo valore del singolo file. Il sonar salva il valore
%         % con una "R" al primo posto, poi il numero in metri ed
%         % eventualmente una "E" alla fine per indicare che il dato è
%         % incerto....
%         % codifica R99.99E vuol dire che non ha ricevuto ritorno
%         if numel(z{j,1})~=0    
%         primo=z{j,1}(1);
%         % ================================================================
%         % le casistiche affrontate sono in continuo upgrade in base alle
%         % anomalie che man mano si riscontrano nei dati restituiti 
%         % dal sonar.....
%         % ================================================================
%         % identificazione del carattere 
%         stringacontrollo=z{j,1};
%         % char(0:255) visualizza tutti i simboli....il 14 è quello che
%         % cerco....trovato per tentativi.....
%         controllo=findstr(stringacontrollo,char(14));
%         if controllo>0
%             simbolostrano=1;
%         else
%             simbolostrano=0;
%         end
%         % se il primo valore della stringa letta è una "R" e non c'è
%         % nessun simbolo strano,leggi l'ultimo
%         if primo=='R' && simbolostrano==0
%             stringa=z{j,1}(2:end);
%             lung=length(stringa);
%             ultimo=stringa(lung);
%             % se la lughezza del dato dopo la R è pari a 3 spazi, il dato è
%             % sempre corretto, esempio R2.3, R0.2, R3.5, ecc....
%             % Alle volte mette lo zero alla fine alle volte no...BOH!!!!!
%             if lung==3
%                 numero(h,1)=str2num(stringa);
%                 h=h+1;
%             end
%             % se la lughezza del dato dopo la R è pari a 4 spazi, il dato è
%             % sempre corretto, esempio R2.35, R0.23, R3.50, ecc....
%             % potrebbe esserci il caso di R2.3E...il programma di incazza!
%             if lung==4
%                 numero(h,1)=str2num(stringa);
%                 h=h+1;
%             end
%             % se la linghezza del dato dopo la R è pari a 5 sono 3 le
%             % configurazioni possibili: 
%             % 1) 13.64 dato buono
%             % 2) 3.44E devo eliminare la E (dato incerto, lo tengo)
%             % 3) 5.66 devo eliminare il simbolo, dato buono
%             % affrontiamo caso per caso
%             if lung==5
%                 if stringa(3)=='.'
%                     numero(h,1)=str2num(stringa);
%                     h=h+1;
%                 else
%                     stringaE=stringa(1:lung-1);
%                     numero(h,1)=str2num(stringaE);
%                     h=h+1;
%                     E=E+1;
%                 end
%             end
%             if lung==6
%                 stringaE=stringa(1:lung-1);
%                 if stringaE=='99.99'
%                     nodato=nodato+1;
%                 else
%                     numero(h,1)=str2num(stringaE);
%                     h=h+1;
%                     E=E+1;
%                 end
%             end
%         end
%         dati(k,1)=z(j,1);
%         k=k+1;
%         % se il primo valore non è una "R" butto il dato...
%         end
%     end
%     tab_sonar(i,1)=i;
%     % Media oraria (il sonar acquisisce una volta al secondo)
%     % se nel file orario sono presenti più della metà di dati non validi
%     % del tipo "R99.99.E" butto il file, quindi media zero!!!!!!!!!!!!!!
%     if nodato>=1800
%        tab_sonar(i,2)=0; 
%     else
%        tab_sonar(i,2)=12.33-(mean(numero(:,1),1));
%     end
%     % Varianza se nel file orario sono presenti più della metà di dati non validi
%     % del tipo "R99.99.E" butto il file, quindi varianza zero!!!!!!!!!!!
%     if nodato>=1800
%        tab_sonar(i,3)=0; 
%     else
%        tab_sonar(i,3)=var(numero(:,1));
%     end
%     % Dati massimi totali all'ora utilizzabili per la media
%     tab_sonar(i,4)=3600;
%     % Dati reali utilizzati per la media
%     % se nel file orario sono presenti più della metà di dati non validi
%     % del tipo "R99.99.E" butto il file, quindi zero dati utili!!!!!!!!!
%     if nodato>=1800
%        tab_sonar(i,5)=0; 
%     else
%        tab_sonar(i,5)=size(numero,1);
%     end
%     % N° dati incerti utilizzati per la media
%     tab_sonar(i,6)=E;
%     % Percentuale di valori utilizzati rispetto ai 3600
%     % che dovrebbero esserci per la media. Se nel file orario sono presenti 
%     % più della metà di dati non validi del tipo "R99.99.E" butto il file,
%     % quindi zero!!!!!!!!!
%     if nodato>=1800
%        tab_sonar(i,7)=0; 
%     else
%         tab_sonar(i,7)=tab_sonar(i,5)/tab_sonar(i,4)*100;
%     end
%     % Percentuale di valori incerti (rispetto ai valori utili)
%     % utilizzati per il calcolo di media e varianza. Se nel file orario 
%     % sono presenti più della metà di dati non validi del tipo "R99.99.E" 
%     % butto il file, quindi zero!!!!!!!!!
%     if nodato>=1800
%        tab_sonar(i,8)=0; 
%     else
%         tab_sonar(i,8)=tab_sonar(i,6)/tab_sonar(i,5)*100;
%     end
%     timing=matrice_analisi_sonar(i,5)+matrice_analisi_sonar(i,6)/60+...
%     matrice_analisi_sonar(i,7)/3600;
%     tab_sonar(i,9)=timing;
%     
%     k=1;
%     h=1;
%     E=0;
%     nodato=0;
%     clear numero;    
% end
% 
% for i=1:size(tab_sonar,1)
%     for j=1:16
%         if j<8
%             matrice_totale_sonar(i,j)=matrice_analisi_sonar(i,j);
%         else
%             matrice_totale_sonar(i,j)=tab_sonar(i,j-7);
%         end
%     end
% end
% 
% clear e; clear ee; clear eee;
% %==========================================================================
% %==========================================================================
% 
% 
% %==========================================================================
% % TELECAMERA MANTOVA ======================================================
% % 1) matrice dir 
% % 2) eliminazione dei primi di file "." ".."
% % 3) eliminazione dei file zero (riavvio)
% % 4) recupero informazioni
% % 5) creazione matrice analisi
% 
% % posizionamento nella cartella di riferimento
% cd(limbo_telma)
% % elenco dei file spostati nella cartella
% e=dir;
% % eliminazione dei primi due ("." "..")
% for i=4:size(e,1)
%     ee(i-3,1)=e(i,1);
% end
% % eliminazione dei file che pesano 0 bytes 
% % eee contiene l'elenco dei file che devono essere elaborati...
% % per le telecamere questo potrebbe essere un filtro per eliminare le
% % immagini troncate e/o rovinate che hanno un peso in bytes inferiore
% % rispetto al "normale"...
% indice=1;
% for i=1:size(ee,1)
%     if ee(i).bytes >0 % ponendo 0 eee è uguale a ee
%     eee(indice,1)=ee(i,1);
%     indice=indice+1;
%     end
% end
% % riduzione della matrice link ai soli file spostati "100"
% indice=1;
% for i=1:size(mat_telma_link,1)
%     if mat_telma_link(i,8)==100
%         for j=1:7
%         matrice_ridotta_telma(indice,j)=mat_telma_link(i,j);
%         end
%         indice=indice+1;
%     end
% end
% % riduzione della matrice_ridotta_telma ai soli file utili (filtro per 
% % generare l'elendo "eee" bytes). 
% for i=1:size(matrice_ridotta_telma,1)
%     stringafile=num2str(matrice_ridotta_telma(i,1));
%     nomefile=strcat('mantova',stringafile,'.jpg');
%     for j=1:size(eee,1)
%         if nomefile==eee(j).name
%             if eee(j).bytes>0
%                 matrice_analisi_telma(j,1)=matrice_ridotta_telma(i,1);
%                 matrice_analisi_telma(j,2)=matrice_ridotta_telma(i,2);
%                 matrice_analisi_telma(j,3)=matrice_ridotta_telma(i,3);
%                 matrice_analisi_telma(j,4)=matrice_ridotta_telma(i,4);
%                 matrice_analisi_telma(j,5)=matrice_ridotta_telma(i,5);
%                 matrice_analisi_telma(j,6)=matrice_ridotta_telma(i,6);
%                 matrice_analisi_telma(j,7)=matrice_ridotta_telma(i,7);
%                 matrice_analisi_telma(j,8)=matrice_ridotta_telma(i,5)+...
%                     matrice_ridotta_telma(i,6)/60+...
%                     matrice_ridotta_telma(i,7)/3600;
%             end
%         end
%     end
% end
% 
% % eliminazione delle matrici e,ee,eee in quanto riutilizzate in seguito    
% clear e; clear ee; clear eee;
% %==========================================================================
% %==========================================================================
% 
% 
% %==========================================================================
% % TELECAMERA MODENA =======================================================
% % 1) matrice dir 
% % 2) eliminazione dei primi di file "." ".."
% % 3) eliminazione dei file zero (riavvio)
% % 4) recupero informazioni
% % 5) creazione matrice analisi
% 
% % posizionamento nella cartella di riferimento
% cd(limbo_telmo)
% % elenco dei file spostati nella cartella
% e=dir;
% % eliminazione dei primi due ("." "..")
% for i=4:size(e,1)
%     ee(i-3,1)=e(i,1);
% end
% % eliminazione dei file che pesano 0 bytes 
% % eee contiene l'elenco dei file che devono essere elaborati...
% % per le telecamere questo potrebbe essere un filtro per eliminare le
% % immagini troncate e/o rovinate che hanno un peso in bytes inferiore
% % rispetto al "normale"...
% indice=1;
% for i=1:size(ee,1)
%     if ee(i).bytes >0 % ponendo 0 eee è uguale a ee
%     eee(indice,1)=ee(i,1);
%     indice=indice+1;
%     end
% end
% % riduzione della matrice link ai soli file spostati "100"
% indice=1;
% for i=1:size(mat_telmo_link,1)
%     if mat_telmo_link(i,8)==100
%         for j=1:7
%         matrice_ridotta_telmo(indice,j)=mat_telmo_link(i,j);
%         end
%         indice=indice+1;
%     end
% end
% % riduzione della matrice_ridotta_telma ai soli file utili (filtro per 
% % generare l'elenco "eee" bytes). 
% for i=1:size(matrice_ridotta_telmo,1)
%     stringafile=num2str(matrice_ridotta_telmo(i,1));
%     nomefile=strcat('modena',stringafile,'.jpg');
%     for j=1:size(eee,1)
%         if nomefile==eee(j).name
%             if eee(j).bytes>0
%                 matrice_analisi_telmo(j,1)=matrice_ridotta_telmo(i,1);
%                 matrice_analisi_telmo(j,2)=matrice_ridotta_telmo(i,2);
%                 matrice_analisi_telmo(j,3)=matrice_ridotta_telmo(i,3);
%                 matrice_analisi_telmo(j,4)=matrice_ridotta_telmo(i,4);
%                 matrice_analisi_telmo(j,5)=matrice_ridotta_telmo(i,5);
%                 matrice_analisi_telmo(j,6)=matrice_ridotta_telmo(i,6);
%                 matrice_analisi_telmo(j,7)=matrice_ridotta_telmo(i,7);
%                 matrice_analisi_telmo(j,8)=matrice_ridotta_telmo(i,5)+...
%                     matrice_ridotta_telmo(i,6)/60+...
%                     matrice_ridotta_telmo(i,7)/3600;
%             end
%         end
%     end
% end
% 
% analisicompletata='Analisi Completata';
% set(handles.text12,'string',analisicompletata);
% 
% % eliminazione delle matrici e,ee,eee in quanto riutilizzate in seguito    
% clear e; clear ee; clear eee;
% 














 
  
%==========================================================================
%==========================================================================
%==========================================================================
%==========================================================================
%==========================================================================

% --- Executes on button press in radiobutton2.
function radiobutton2_Callback(hObject, eventdata, handles)
% hObject    handle to radiobutton2 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of radiobutton2


% --- Executes on button press in radiobutton3.
function radiobutton3_Callback(hObject, eventdata, handles)
% hObject    handle to radiobutton3 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of radiobutton3


% --- Executes on button press in radiobutton4.
function radiobutton4_Callback(hObject, eventdata, handles)
% hObject    handle to radiobutton4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of radiobutton4


% --- Executes on button press in radiobutton5.
function radiobutton5_Callback(hObject, eventdata, handles)
% hObject    handle to radiobutton5 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of radiobutton5


