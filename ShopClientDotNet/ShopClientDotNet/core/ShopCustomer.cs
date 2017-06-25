using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShopClientDotNet.domain;

namespace ShopClientDotNet
{
    public class ShopCustomer
    {
        private CustomerService customerService = new CustomerServiceImpl();
        private int customerNumber = 0;
        private string shopChannel = "";
        

        public PetOrder createOrder()
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

        public CustomerService getCustomerService()
        {
            return customerService;
        }

        public int CustomerNumber { get { return customerNumber; } set { this.customerNumber = value; } }
        public string ShopChannel { get { return shopChannel; } set { this.shopChannel = value; } }
    }
}
