using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopClientDotNet
{
    public class Shopping
    {
        public static bool isShopClosed = false;
        private Random shopChannelChoice = new Random();

        static void Main(string[] args)
        {
            Shopping shopping = new Shopping();
            shopping.StartShopping();
        }

        private void StartShopping()
        {
            Console.WriteLine("start shopping...");
            int customerNumber = 0;
            ShopCustomer customer = new ShopCustomer();
            customer.ShopChannel = this.GetShopChannel(0);
            customer.CustomerNumber = ++customerNumber;
            customer.run();


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
