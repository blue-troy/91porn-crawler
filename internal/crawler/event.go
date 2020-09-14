package crawler

type EventCenter struct {
	executors []func(data interface{})
	dataChan  chan interface{}
}

func (c *EventCenter) AddExecutor(f func(data interface{})) {
	c.executors = append(c.executors, f)
}

func (c *EventCenter) Publish(data interface{}) {
	c.dataChan <- data
}

func (c *EventCenter) action() {
	for {
		data := <-c.dataChan
		for _, ex := range c.executors {
			go ex(data)
		}
	}
}

func NewEventCenter() *EventCenter {
	e := &EventCenter{dataChan: make(chan interface{}, 100)}
	go e.action()
	return e
}
