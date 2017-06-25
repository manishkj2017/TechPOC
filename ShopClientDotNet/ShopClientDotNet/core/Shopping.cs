using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using static ShopClientDotNet.ShopCustomer;

namespace ShopClientDotNet
{
    public class Shopping
    {
        public static bool isShopClosed = false;
        private Random shopChannelChoice = new Random();
        private const int maxCustomers = 1000; //need to move to property file
        static volatile int ThreadCompleteCount = 0;
        

        static void Main(string[] args)
        {
            Shopping shopping = new Shopping();
            shopping.StartShopping();
        }

        private void StartShopping()
        {
            Console.WriteLine("start shopping...");
            int maxWorker; int maxIOC;
            ThreadPool.GetMinThreads(out maxWorker, out maxIOC);
            Console.WriteLine(maxWorker + ":" + maxIOC);
            for (int i=1; i<= maxCustomers; i++)
            {
                ShopCustomer customer = new ShopCustomer();
                customer.ShopChannel = this.GetShopChannel(0);
                customer.CustomerNumber = i;
                //Thread custThread = new Thread(customer.run);
                //custThread.Start();
                ThreadPool.QueueUserWorkItem(new WaitCallback(run), customer);
            }

            while(ThreadCompleteCount != maxCustomers)
            {
                Console.WriteLine("waiting for customers to finish shopping..");
                Console.WriteLine("remaining customers - " + (maxCustomers - ThreadCompleteCount));
                Thread.Sleep(1000);
            }

        }

        private void run(object callbackObject)
        {
            ShopCustomer customer = (ShopCustomer)callbackObject;
            Console.WriteLine("customer - " + customer.CustomerNumber + " is shopping");
            ShopChannel shopChannel = customer.getCustomerService().GetCustomerInterfaceForPets(customer.ShopChannel);
            if (shopChannel.IsShopClosed())
            {
                Console.WriteLine("[Customer " + customer.CustomerNumber + "] : returning as shop is closed");
                ThreadCompleteCount++;
                return;
            }

            //raise order
            shopChannel.Order(customer.createOrder());
            ThreadCompleteCount++;
        }

       
        private string GetShopChannel(int channelNumber)
        {
            switch(channelNumber)
            {
                case 0:
                    return "WEB";
            }
            return "";
        }
    }
}
