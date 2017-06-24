using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShopClientDotNet.domain;

namespace ShopClientDotNet
{
    class ShopCustomer
    {
        private CustomerService customerService = new CustomerServiceImpl();
        private int customerNumber = 0;
        private string shopChannel = "";

        private PetOrder createOrder()
        {
            PetOrder order = new PetOrder();
            Pets pet = this.getCustomerService().GetPetChoice();

            order.BidPrice = this.getCustomerService().GetBidPrice(pet);
            order.CustomerNumber = this.CustomerNumber;
            order.CustomerName = "Customer" + this.CustomerNumber;
            order.OrderNumber = this.CustomerNumber;
            order.Status = "Pending";
            order.PetType = this.getCustomerService().GetPetName(pet);
            return order;
        }

        private CustomerService getCustomerService()
        {
            return customerService;
        }

        public void run()
        {
            ShopChannel shopChannel = this.getCustomerService().GetCustomerInterfaceForPets(this.ShopChannel);
            if (shopChannel.IsShopClosed())
            {
                Console.WriteLine("[Customer " + this.CustomerNumber + "] : returning as shop is closed");
                return;
            }

            //raise order
            shopChannel.Order(createOrder());
        }

        public int CustomerNumber { get { return customerNumber; } set { this.customerNumber = value; } }
        public string ShopChannel { get { return shopChannel; } set { this.shopChannel = value; } }
    }
}
