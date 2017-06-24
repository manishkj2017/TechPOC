using System;
using ShopClientDotNet.domain;

namespace ShopClientDotNet
{
    
    internal class WEBShopChannel : ShopChannel

    {
        private static bool isShopClosed = false;

        public bool IsShopClosed()
        {
            return isShopClosed;
        }

        public void Order(PetOrder order)
        {
            Console.WriteLine("Web service needs to be implemented");
        }
    }
}