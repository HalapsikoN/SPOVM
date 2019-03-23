#include <iostream>
#include <fstream>
#include <random>
#include <Windows.h>


using namespace std;

int main(int argc, char* argv[])
{
	//system("start cmd");
	const int KOL = 10;
	cout << "======CHILD PROCESS START======" << endl;
	int arr[KOL];
	random_device rd;
	//mt19937 mt(rd());
	//uniform_int_distribution<int> dist(0, 100);

	for (int i = 0; i < KOL; ++i)
	{
		arr[i] = rd() % 100;
	}

	fstream file("data.txt", ios::out);

	if (!file.is_open())
	{
		cout << "There are problems with file :(" << endl;
	}

	cout << "Array in CHILD process: ";
	for (int i = 0; i < KOL; ++i)
	{
		cout << arr[i] << " ";
		file << arr[i] << " ";
	}
	cout << endl;
	file.close();
	//Sleep(15000);
	cout << "======CHILD PROCESS END======" << endl;

	return 0;
}