//////////SOURCE CODE FOR SCILAB LIBRARY
clear()
index=scf()
set(index,"figure_name","Scibrary")
//////////
delmenu(index.figure_id,gettext('File'))
delmenu(index.figure_id,gettext('?'))
delmenu(index.figure_id,gettext('Tools'))
toolbar(index.figure_id,'off')
handles.dummy = 0;
handles.frame=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2359375,0.2958333,0.421875,0.50625],'Relief','default','SliderStep',[0.01,0.1],'String','UnName1','Style','frame','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','frame','Callback','')
handles.name=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.3546875,0.65,0.2796875,0.0666667],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','name','Callback','')
handles.pass=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.35625,0.5145833,0.2796875,0.0666667],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','pass','Callback','')
handles.name_id=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2484375,0.65,0.0859375,0.0541667],'Relief','default','SliderStep',[0.01,0.1],'String','Name-','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','name_id','Callback','')
handles.pass_id=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.246875,0.5166667,0.10625,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','Password-','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','pass_id','Callback','')
handles.login=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.3546875,0.3729167,0.1984375,0.0416667],'Relief','default','SliderStep',[0.01,0.1],'String','Login','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','login','Callback','login_callback(handles)')
handles.date=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2546875,0.7297674,0.2546875,0.0627907],'Relief','default','SliderStep',[0.01,0.1],'String','Date-','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','date','Callback','')
str_dt=date();
handles.date_show=uicontrol(index,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.3192188,0.7297674,0.285625,0.0627907],'Relief','default','SliderStep',[0.01,0.1],'String',str_dt,'Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','date_show','Callback','')
//////////
///LOGIN???

function login_callback(handles)

//Login Verification
realtimeinit(0.3)
wait=waitbar("Verifying Credentials....")
for i=0:0.1:1,
    realtime(3*i)
    waitbar(i,wait)
end
close(wait)
close(index)
cd("H:\SCILAB\")
////////////////////
////////////////////
////MENU INITIATION////
////////////
menu=scf()
set(menu,"figure_name","Menu")
//////////
delmenu(menu.figure_id,gettext('File'))
delmenu(menu.figure_id,gettext('?'))
delmenu(menu.figure_id,gettext('Tools'))
toolbar(menu.figure_id,'off')
handles.dummy = 0;
handles.menu=uicontrol(menu,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2,0.0998333,0.62,0.7883333],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','frame','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','menu','Callback','')
handles.add=uicontrol(menu,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.345625,0.63125,0.3375,0.1333333],'Relief','default','SliderStep',[0.01,0.1],'String','Add Record','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','add','Callback','add_callback(handles)')
handles.view=uicontrol(menu,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.345625,0.428125,0.3375,0.1333333],'Relief','default','SliderStep',[0.01,0.1],'String','View Record(s)','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','view','Callback','view_callback(handles)')
handles.graph=uicontrol(menu,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.345625,0.225,0.3375,0.1333333],'Relief','default','SliderStep',[0.01,0.1],'String','Graph','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','graph','Callback','graph_callback(handles)')
handles.delete=uicontrol(menu,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.345625,0.025,0.3375,0.1333333],'Relief','default','SliderStep',[0.01,0.1],'String','Delete Data','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','delete','Callback','delete_callback(handles)')
endfunction
///////////
/////////////
///////////////ADD//////
/////////////
/////////
function add_callback(handles)

cd("H:\SCILAB\")
clc()
add=scf()
set(add,"figure_name","Add Record")
//////////
delmenu(add.figure_id,gettext('File'))
delmenu(add.figure_id,gettext('?'))
delmenu(add.figure_id,gettext('Tools'))
toolbar(add.figure_id,'off')
handles.dummy = 0;
handles.frame1=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.1,0.0166667,0.8015625,0.8770833],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','frame','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','frame1','Callback','')
handles.title=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[25],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.3484375,0.7720833,0.2603125,0.2183333],'Relief','default','SliderStep',[0.01,0.1],'String','ADD RECORD','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','title','Callback','')
handles.book_title=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.11125,0.6708333,0.209375,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','Title Of Book -','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','book_title','Callback','')
handles.book_code=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.11125,0.7758333,0.19375,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','Book Code -','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','book_code','Callback','')
handles.book_name=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[18],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2882812,0.6770833,0.5871875,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','book_name','Callback','')
handles.bcode=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[18],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2882812,0.7770833,0.5871875,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','ISBN ','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','str_code','Callback','')
handles.stuname=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.115,0.5708333,0.1609375,0.0458333],'Relief','default','SliderStep',[0.01,0.1],'String','Name Of Student-','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','stuname','Callback','')
handles.stu_name=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[18],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2882812,0.578125,0.5871875,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','stu_name','Callback','')
handles.id=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.115625,0.4729167,0.1265625,0.0458333],'Relief','default','SliderStep',[0.01,0.1],'String','ID no -','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','id','Callback','')
handles.id#=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[18],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2859375,0.4791667,0.5871875,0.05625],'Relief','default','SliderStep',[0.01,0.1],'String','RA','Style','edit','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','id#','Callback','')
handles.issue=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.1140625,0.3808333,0.1625,0.0333333],'Relief','default','SliderStep',[0.01,0.1],'String','Issue Date -','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','issue','Callback','')
str_date=date()
handles.is_date=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[18],'FontUnits','points','FontWeight','normal','ForegroundColor',[1,0,1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2859375,0.3775,0.3015625,0.0479167],'Relief','default','SliderStep',[0.01,0.1],'String',str_date,'Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','is_date','Callback','')
handles.return=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.1140625,0.3083333,0.143125,0.0375],'Relief','default','SliderStep',[0.01,0.1],'String','Return After-','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','return','Callback','')
handles.week1=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2853125,0.2064537,0.1308125,0.0572687],'Relief','default','SliderStep',[0.01,0.1],'String','1 Week','Style','radiobutton','Value',[1],'VerticalAlignment','middle','Visible','on','Tag','week1','Callback','week1_callback(handles)')
handles.week2=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.4351042,0.2064537,0.1308125,0.0572687],'Relief','default','SliderStep',[0.01,0.1],'String','2 Weeks','Style','radiobutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','week2','Callback','week2_callback(handles)')
handles.week4=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.5948958,0.2157709,0.1308125,0.0386344],'Relief','default','SliderStep',[0.01,0.1],'String','4 Weeks','Style','radiobutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','week4','Callback','week4_callback(handles)')
handles.others=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.7546875,0.2168722,0.1308125,0.0364317],'Relief','default','SliderStep',[0.01,0.1],'String','Other','Style','radiobutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','others','Callback','others_callback(handles)')
handles.iss_btn=uicontrol(add,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.625,0.0374449,0.21875,0.0726872],'Relief','default','SliderStep',[0.01,0.1],'String','Issue','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','iss_btn','Callback','iss_btn_callback(handles)')
endfunction
//////////


funcprot(0)
function week1_callback(handles)
//Object initiation for radiobuttons
w1=findobj('tag','week1')
w2=findobj('tag','week2')
w4=findobj('tag','week4')
wo=findobj('tag','others')
set(w2,"value",[0])
set(w4,"value",[0])
set(wo,"value",[0])
//xsave(add,"C:\Scibrary\temp.xml")
//xload("C:\Scibrary\temp.xml",add)

endfunction


function week2_callback(handles)
w1=findobj('tag','week1')
w2=findobj('tag','week2')
w4=findobj('tag','week4')
wo=findobj('tag','others')
set(w1,"value",[0])
set(w4,"value",[0])
set(wo,"value",[0])
//xsave(f,"C:\Scibrary\temp.xml")
//xload("C:\Scibrary\temp.xml",f)

endfunction


function week4_callback(handles)
w1=findobj('tag','week1')
w2=findobj('tag','week2')
w4=findobj('tag','week4')
wo=findobj('tag','others')
set(w1,"value",[0])
set(w2,"value",[0])
set(wo,"value",[0])
//xsave(add,"C:\Scibrary\temp.xml")
//xload("C:\Scibrary\temp.xml",add)
endfunction


function others_callback(handles)
w1=findobj('tag','week1')
w2=findobj('tag','week2')
w4=findobj('tag','week4')
wo=findobj('tag','others')
set(w1,"value",[0])
set(w2,"value",[0])
set(w4,"value",[0])
set(w1,"enable","off")
set(w2,"enable","off")
set(w4,"enable","off")
//xsave(add,"C:\Scibrary\temp.xml")
//xload("C:\Scibrary\temp.xml",add)
endfunction


function iss_btn_callback(handles)
    
//Objects for Output

//Id number
id=findobj('tag','id#')

//Student name
name=findobj('tag','stu_name')

//Issue date
issdt=findobj('tag','is_date')

//Book code
bcode=findobj('tag','str_code')


 ///Formatted Output Strings To be Inserted in text File
//Format= 10*Book code + 15*id + 20*name + IssueDate
str_bcode=part(bcode.string,1:10)
str_id=part(id.string,1:15)
str_name=part(name.string,1:20)
disp("Book issued !",id.string)
//////
/////
File=mopen("DB.txt",'at')
mfprintf(File,'%s/%s/%s/%s/\n',str_bcode,str_id,str_name,issdt.string)
mclose()
close(scf())

endfunction



/////////////////
///////////////VIEW//////
/////////////
/////////

function view_callback(handles)

cd("H:\SCILAB\")
view=scf()
set(view,"figure_name","View Record")
//////////
delmenu(view.figure_id,gettext('File'))
delmenu(view.figure_id,gettext('?'))
delmenu(view.figure_id,gettext('Tools'))
toolbar(view.figure_id,'off')
handles.dummy = 0;          //dummy handler for returning handles to functions
handles.frame1=uicontrol(view,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2,0.0083333,0.7,0.8895833],'Relief','default','SliderStep',[0.01,0.1],'String','UnName1','Style','frame','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','frame1','Callback','')
handles.updt=uicontrol(view,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.2375,0.0920833,0.1478125,0.0583333],'Relief','default','SliderStep',[0.01,0.1],'String','Update','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','del_rec','Callback','update_list()')
handles.list=uicontrol(view,'unit','normalized','BackgroundColor',[1,1,0],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[16],'FontUnits','points','FontWeight','normal','ForegroundColor',[0,0,1],'HorizontalAlignment','left','ListboxTop',[1],'Max',[1],'Min',[1],'Position',[0.225,0.1666667,0.65,0.60625],'Relief','default','SliderStep',[0.01,0.1],'String',[],'Style','listbox','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','list','Callback','list_callback(handles)')
handles.title=uicontrol(view,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[25],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','left','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.4609375,0.78125,0.3859375,0.09375],'Relief','default','SliderStep',[0.01,0.1],'String','RECORDS','Style','text','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','title','Callback','')
handles.ok=uicontrol(view,'unit','normalized','BackgroundColor',[-1,-1,-1],'Enable','on','FontAngle','normal','FontName','Tahoma','FontSize',[12],'FontUnits','points','FontWeight','normal','ForegroundColor',[-1,-1,-1],'HorizontalAlignment','center','ListboxTop',[],'Max',[1],'Min',[0],'Position',[0.7390625,0.0270833,0.1296875,0.10625],'Relief','default','SliderStep',[0.01,0.1],'String','Exit','Style','pushbutton','Value',[0],'VerticalAlignment','middle','Visible','on','Tag','ok','Callback','exit_callback(handles)')

endfunction
//////////
function update_list(handles)
//////////
/////LIST UPDATION/////
tab=findobj('tag','list')
i=0;
is=isfile("DB.txt")
mclose()
if is==%f then
    tab.string(1)="Database is Corrupted or Erased!"
end
fd=mopen("DB.txt",'rt')
mseek(0,fd)
////Upto 10 Records
for j=1:1:20 do
    i=i+1
tab.string(i)=""
put=""
//////ISBN CODE UPDATION
ch=mgetstr(1,fd)
    while ch~='/'
        put=put + ch
        ch=mgetstr(1,fd)
    end
    tab.string(i)=put
///////ROLL NUMBER_ LIST UPDATION
put=""
ch=mgetstr(1,fd)
    while ch~='/'
        put=put + ch
        ch=mgetstr(1,fd)
    end
    tab.string(i)=tab.string(i)+"                     "+put
////////NAME UPDATION
        put=""
ch=mgetstr(1,fd)
    while ch~='/'
            put=put+ch
            ch=mgetstr(1,fd)
        end
   tab.string(i)=tab.string(i)+"                      "+put 
//////ISSUE DATE UPDATION
 put=""
ch=mgetstr(1,fd)
    while ch~='/'
            put=put+ch
            ch=mgetstr(1,fd)
        end
   tab.string(i)=tab.string(i)+"                      "+put  
end
mclose()
endfunction

function return_callback(handles)
if tab.value==2  then
     
     tab.string(2)=tab.string(3)
     tab.string(3)=tab.string(4)
    end
endfunction


function list_callback(handles)
 
endfunction


function exit_callback(handles)
close(scf())
endfunction


function delete_callback(handles)
deletefile("DB.txt")
endfunction


///////////GRAPH///////
////////
////////
function graph_callback(handles)
f=scf()
fl=isfile("graph.xml")
mclose()
if fl==%t then
    xload("graph.xml")
end
dt=getdate() ///x variable
txt=mgetl("DB.txt")
mclose()
disp(txt)
len=length(length(txt))
bar(dt(3),len)
xsave("graph.xml",f)
endfunction

