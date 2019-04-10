#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <unistd.h>
#include <cstring>
#include <string.h>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <limits>
#include <semaphore.h>
#include <stdio.h>
#include <fcntl.h>

//#include <caca_conio.h>

#define CHILD_PATH "/home/halapsikon/CLionProjects/luk/cmake-build-debug/luk"
#define KEY_NAME "/dev/null"
#define SERVER_SEMAPHORE_INDEX 0
#define CLIENT_SEMAPHORE_INDEX 1
#define CLIENT_ERROR_SEMAPHORE_INDEX 2
#define KILL_SEMAPHORE_INDEX 3
#define SIZE 20


using namespace std;

//const short array[4]={0};

void deleteSemaphore(int id)
{
    semctl(id, 0, IPC_RMID, NULL);
}

/*
void* mapSharedMemory(int shmId)
{
    void* memoryAddress;

    memoryAddress = shmat(shmId, NULL, 0);

    return memoryAddress;
}

int createSemaphoreSet(key_t key)
{

    int id;
    int check = 0;

    id = semget(key, 4, IPC_CREAT | SHM_R | SHM_W);
    if (id != -1)
    {
        check = semctl(id, 0, SETALL,
                       const_cast<short*>(array)); // SETALL ignores
        // semnum (second) argument
    }

    return (check == -1) ? check : id;
}
*/


int main(int argc, char** argv)
{
    int status;
    pid_t pid;
    key_t keySem, keyShared;
    int semId, sharedId;
    void* memoryAdress;
    struct sembuf semStruct;
    struct shmid_ds memoryStruct;


    //второй параметр id просто так 40
    keySem=ftok(KEY_NAME, 40);
    if(keySem==(key_t)-1)
    {
        cout<<"ftok YMER"<<endl;
        return 0;
    }
    string str=to_string(keySem);
    argv[1] = const_cast<char*>(str.c_str());
    //
    keyShared=ftok(KEY_NAME, 41);
    if(keyShared==(key_t)-1)
    {
        cout<<"ftok YMER 2"<<endl;
        return 0;
    }
    string str1=to_string(keyShared);
    argv[2]= const_cast<char*>(str1.c_str());

   // cout<<"(f)"<<keySem<<"  "<<argv[1]<<endl;
 //   cout<<"(f)"<<keyShared<<"   "<<argv[2]<<endl;

    //создание семафора ключ, количество семафор, создание
    semId = semget(keySem, 4, IPC_CREAT | SHM_R | SHM_W);
    if(semId==-1)
    {
        cout<<"semget YMER"<<endl;
        return 0;
    }

    //создание памяти
    sharedId = shmget(keyShared, SIZE, IPC_CREAT | SHM_R | SHM_W);
    if(sharedId==-1)
    {
        cout<<"shmget YMER"<<endl;
        //удаление всех ожидающих семафор начиная с 0
        deleteSemaphore(semId);
        return 0;
    }

    //адрес памяти
    memoryAdress=shmat(sharedId, NULL, 0);
    if(memoryAdress==NULL)
    {
        cout<<"Cheto ne to s pam'at'u"<<endl;
        deleteSemaphore(semId);
        shmctl(sharedId, IPC_RMID, &memoryStruct);
        return 0;
    }

    //cout<<"(f)"<<(char*)memoryAdress<<"  "<<argv[1]<<endl;
    //cout<<"(f)"<<sharedId<<"   "<<argv[2]<<endl;

    semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
    semStruct.sem_op=1;
    semStruct.sem_flg=SEM_UNDO;
    semop(semId, &semStruct, 1);
    semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
    semStruct.sem_op=1;
    semStruct.sem_flg=SEM_UNDO;
    semop(semId, &semStruct, 1);

    /*cout<<"(f)"<<semctl(semId, SERVER_SEMAPHORE_INDEX, GETVAL)<<endl;
    //cout<<"(LUK)"<<sharedId<<"   "<<argv[2]<<endl;
    cout<<"(f)"<<semctl(semId, CLIENT_SEMAPHORE_INDEX, GETVAL)<<endl;
    cout<<"(f)"<<semctl(semId, CLIENT_ERROR_SEMAPHORE_INDEX, GETVAL)<<endl;
    cout<<"(f)"<<semctl(semId, KILL_SEMAPHORE_INDEX, GETVAL)<<endl;*/

    pid=fork();
    switch(pid)
    {
        case -1:
        {
            deleteSemaphore(semId);
            //отстыковка памяти
            shmdt(memoryAdress);
            shmctl(sharedId, IPC_RMID, &memoryStruct);
            return 0;
        }
        case 0:
        {
            /*while(true)
            {
                //ожидание сервера
                //индекс сервера
                semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
                semStruct.sem_op=0;
                semStruct.sem_flg=SEM_UNDO;
                semop(semId, &semStruct, 1);
                semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
                semStruct.sem_op=1;
                semStruct.sem_flg=SEM_UNDO;
                semop(semId, &semStruct, 1);

              //  cout<<endl<<i<<endl;

                //ожидание инфы
                if(semctl(semId, KILL_SEMAPHORE_INDEX, GETVAL)==1)
                {
                    shmdt(memoryAdress);
                    return 0;
                }


                //походу чтение памяти
                memoryAdress=(char*)shmat(sharedId, NULL, 0);


                if(memoryAdress==NULL)
                {
                    semStruct.sem_num=CLIENT_ERROR_SEMAPHORE_INDEX;
                    semStruct.sem_op=1;
                    semStruct.sem_flg=SEM_UNDO;
                    semop(semId, &semStruct, 1);

                    semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
                    semStruct.sem_op=-1;
                    semStruct.sem_flg=SEM_UNDO;
                    semop(semId, &semStruct, 1);

                    return 1;
                }

                cout<<"A Client takoi tip: "<<(char*)memoryAdress<<endl;

                semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
                semStruct.sem_op=-1;
                semStruct.sem_flg=SEM_UNDO;
                semop(semId, &semStruct, 1);
            }
            return 0;*/
            execv(CHILD_PATH, argv);
            break;
        }
        default:
        {
            int currPos=0;
            bool readyInput= true;
            string str;
            str.resize(SIZE,'\0');

            while(true) {

                //заполнение памяти
                memset(memoryAdress, '\0', 1);

                /* if(readyInput)
                 {
                     currPos=0;
                     cout<<"Server tip takoy: ny pls input strochku "<<endl;
                     cin>>str;
                     readyInput= false;
                 }

                 string temp;
                 int newLength=0;
                 temp.append(str,0, SIZE-1);
                 currPos=temp.length();
                 strcpy((char*)memoryAdress, const_cast<char*>(temp.c_str()));

                 temp.clear();
                 newLength=str.length()-currPos;
                 if(newLength>0)
                 {
                     temp.append(str,currPos,newLength);
                 }
                 str=temp;*/

                //if(readyInput)
                //{
                cout << "Server: input strochku " << endl;
                cin >> str;
                //}
                strcpy((char *) memoryAdress, const_cast<char *>(str.c_str()));
                str.clear();

                //cout<<semctl(semId, SERVER_SEMAPHORE_INDEX, GETVAL)<<endl;
                //принял инпут
                /*semStruct.sem_num = SERVER_SEMAPHORE_INDEX;
                semStruct.sem_op = 1;
                semStruct.sem_flg = SEM_UNDO;
                semop(semId, &semStruct, 1);*/

                //cout<<semctl(semId, SERVER_SEMAPHORE_INDEX, GETVAL)<<endl;
                //cout<<semctl(semId, CLIENT_SEMAPHORE_INDEX, GETVAL)<<endl;
                //cout<<semctl(semId, KILL_SEMAPHORE_INDEX, GETVAL)<<endl;

                semStruct.sem_num = SERVER_SEMAPHORE_INDEX;
                semStruct.sem_op = -1;
                semStruct.sem_flg = SEM_UNDO;
                semop(semId, &semStruct, 1);


                //ждёт  клиента
                semStruct.sem_num = CLIENT_SEMAPHORE_INDEX;
                semStruct.sem_op = 0;
                semStruct.sem_flg = SEM_UNDO;
                semop(semId, &semStruct, 1);
                semStruct.sem_num = CLIENT_SEMAPHORE_INDEX;
                semStruct.sem_op = 1;
                semStruct.sem_flg = SEM_UNDO;
                semop(semId, &semStruct, 1);

                if (semctl(semId, CLIENT_ERROR_SEMAPHORE_INDEX, GETVAL) == 1) {
                    break;
                }

                //readyInput= true;
                cout << "Press q to leave out, or any to continue" << endl;

                char quest;
                cin>>quest;

                if (quest == 'q') {
                    //тип закончить команду
                    semStruct.sem_num = KILL_SEMAPHORE_INDEX;
                    semStruct.sem_op = 1;
                    semStruct.sem_flg = SEM_UNDO;
                    semop(semId, &semStruct, 1);

                    semStruct.sem_num = SERVER_SEMAPHORE_INDEX;
                    semStruct.sem_op = -1;
                    semStruct.sem_flg = SEM_UNDO;
                    semop(semId, &semStruct, 1);

                    waitpid(pid, &status, 0);
                    if (WIFEXITED(status)) {
                        cout << "Client tyt skazal: " << WEXITSTATUS(status) << endl;
                    }
                    break;

                }
                str.clear();

                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
                cout << endl;
            }
        }
    }

    deleteSemaphore(semId);
    //отстыковка памяти
    shmdt(memoryAdress);
    shmctl(sharedId, IPC_RMID, &memoryStruct);
    return 0;
}