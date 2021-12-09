using System;
using System.Collections.Generic;
using System.Text;

namespace NumberCrunchDotNet
{
    class Node<T>
    {
        public T _tPayload;
        public Node<T> _nodeNext;
    }
    class Queue<T>
    {
        LinkedList<T> _ll;
        public Queue()
        {
            _ll = new LinkedList<T>();
        }
        public void Enqueue(T tPayload)
        {
            _ll.Append(tPayload);
        }
        public T Dequeue()
        {
            T tRet = _ll.Remove(0);
            return tRet;
        }
        public int GetCount()
        {
            return _ll.GetCount();
        }
    }
    class LinkedList<T>
    {
        public Node<T> _nodeHead;
        public Node<T> _nodeTail;
        public int _jCount;
        public LinkedList()
        {
            _nodeHead = new Node<T>();
            _nodeHead._tPayload = default(T);
            _nodeHead._nodeNext = null;
            _nodeTail = _nodeHead;
            _jCount = 0;
        }
        public void Append(T tPayload)
        {
            Node<T> node = new Node<T>();
            node._tPayload = tPayload;
            _nodeTail._nodeNext = node;
            node._nodeNext = null;
            _nodeTail = node;
            _jCount++;
        }
        public int GetCount()
        {
            return _jCount;
        }
        public T Remove(int jPosition)
        {
            T tRet = default(T);
            if ((jPosition < 0) || (jPosition >= _jCount))
            {
                throw new Exception("(jPosition < 0) || (jPosition >= _jCount)");
            }
            int jPosCur = -1;
            Node<T> nodeNext;
            Node<T> nodeHead = _nodeHead;
            do
            {
                nodeNext = nodeHead._nodeNext;
                if (nodeNext != null)
                {
                    if (jPosCur + 1 == jPosition)
                    {
                        tRet = nodeHead._nodeNext._tPayload;
                        nodeHead._nodeNext = nodeHead._nodeNext._nodeNext;
                        _jCount--;
                        break;
                    }
                    jPosCur++;
                    nodeHead = nodeNext;
                }
            } while (nodeNext != null);
            return tRet;
        }
        public void Set(int jPosition, T tPayLoad)
        {
            if ((jPosition < 0) || (jPosition >= _jCount))
            {
                throw new Exception("(jPosition < 0) || (jPosition >= _jCount)");
            }
            int jPosCur = -1;
            Node<T> nodeNext;
            Node<T> nodeHead = _nodeHead;
            do
            {
                nodeNext = nodeHead._nodeNext;
                if (nodeNext != null)
                {
                    if (jPosCur + 1 == jPosition)
                    {
                        nodeHead._nodeNext._tPayload = tPayLoad;
                        break;
                    }
                    jPosCur++;
                    nodeHead = nodeNext;
                }
            } while (nodeNext != null);

        }
        public T Get(int jPosition)
        {
            T tRet = default(T);
            if ((jPosition < 0) || (jPosition >= _jCount))
            {
                throw new Exception("(jPosition < 0) || (jPosition >= _jCount)");
            }
            int jPosCur = -1;
            Node<T> nodeNext;
            Node<T> nodeHead = _nodeHead;
            do
            {
                nodeNext = nodeHead._nodeNext;
                if (nodeNext != null)
                {
                    if (jPosCur + 1 == jPosition)
                    {
                        tRet = nodeHead._nodeNext._tPayload;
                        break;
                    }
                    jPosCur++;
                    nodeHead = nodeNext;
                }
            } while (nodeNext != null);
            return tRet;
        }


    }
}
